package pl.kubisiak.ui.postslist

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.rxjava3.disposables.Disposable
import pl.kubisiak.dataflow.Pager
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.ui.dagger.RootComponent
import pl.kubisiak.ui.BaseViewModel
import pl.kubisiak.ui.items.LoadingItemViewModel
import pl.kubisiak.ui.items.PostItemViewModel


class PostsListViewModel(val blogID: Blog.ID) : BaseViewModel() {

    private var _list: ObservableList<BaseViewModel>? = ObservableArrayList<BaseViewModel>()
    var list: ObservableList<BaseViewModel>?
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
            val retval = ObservableArrayList<BaseViewModel>()
            list = retval
            disposer.add(subscribeToPager(pager, retval) { PostItemViewModel(it) })
            subscribeLoader(pager.requestNextPage())//only the first page gets big loader
        })
        subscribeLoader(postsForBlog.ensure())
    }

    companion object {
        fun <I> subscribeToPager(pager: Pager<List<I>>, output: ObservableList<BaseViewModel>, transform: (I) -> BaseViewModel): Disposable {
            return pager.nextPage().subscribe {
                output.apply {
                    (lastOrNull() as? LoadingItemViewModel)?.also { remove(it) }
                    addAll(it.map(transform))
                    add(LoadingItemViewModel(pager))
                }
            }
        }
    }
}

