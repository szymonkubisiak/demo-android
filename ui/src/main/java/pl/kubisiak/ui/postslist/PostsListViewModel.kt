package pl.kubisiak.ui.postslist

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.disposables.Disposable
import pl.kubisiak.dataflow.Depot
import pl.kubisiak.dataflow.Pager
import pl.kubisiak.dataflow.Source
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.ui.BaseSubViewModel
import pl.kubisiak.ui.BaseViewModel
import pl.kubisiak.ui.Navigator
import pl.kubisiak.ui.dagger.RootComponent
import pl.kubisiak.ui.items.LoadingItemViewModel
import pl.kubisiak.ui.items.PostItemViewModel


class PostsListViewModel @AssistedInject constructor(
    @Assisted val blogID: Blog.ID,
    navigator: Navigator,
    //Why it doesn't work?!?:
    //depot: Depot<Blog.ID, Source<Pager<List<Post.ID>>>>,
) : BaseViewModel(navigator) {

    @AssistedFactory
    interface Factory {
        fun get(blogID: Blog.ID): PostsListViewModel
    }

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

    private val postsForBlog: Source<Pager<List<Post.ID>>>

    init {
        val depot = RootComponent.instance.postsFroBlogPaginatedDepot()
        postsForBlog = depot.get(blogID)
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

