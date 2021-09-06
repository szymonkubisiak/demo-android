package pl.kubisiak.ui.postslist

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.disposables.Disposable
import pl.kubisiak.dataflow.Pager
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.ui.dagger.RootComponent
import pl.kubisiak.ui.BaseSubViewModel
import pl.kubisiak.ui.BaseViewModel
import pl.kubisiak.ui.items.LoadingItemViewModel
import pl.kubisiak.ui.items.PostItemViewModel


class PostsListViewModel(val blogID: Blog.ID) : BaseViewModel() {

    private var _list: ObservableList<BaseSubViewModel>? = ObservableArrayList<BaseSubViewModel>()
    var list: ObservableList<BaseSubViewModel>?
        @Bindable get() = _list
        @Bindable set(value) {
            _list = value
            notifyPropertyChanged(BR.list)
        }

    fun forceRefresh() {
        subscribeLoader(postsForBlog.update())
    }

    private val postsForBlog = RootComponent.instance.postsFroBlogPaginatedDepot().get(blogID)

    init {
        disposer.add(postsForBlog.observable().subscribe { pager ->
            val retval = ObservableArrayList<BaseSubViewModel>()
            list = retval
            disposer.add(subscribeToPager(pager, retval) { PostItemViewModel(disposer, it) })
            subscribeLoader(pager.requestNextPage())//only the first page gets big loader
        })
        subscribeLoader(postsForBlog.ensure())
    }


    fun <I> subscribeToPager(pager: Pager<List<I>>, output: ObservableList<BaseSubViewModel>, transform: (I) -> BaseSubViewModel): Disposable {
        return pager.nextPage().subscribe {
            output.apply {
                (lastOrNull() as? LoadingItemViewModel)?.also { remove(it) }
                addAll(it.map(transform))
                add(LoadingItemViewModel(disposer, pager))
            }
        }
    }
}

