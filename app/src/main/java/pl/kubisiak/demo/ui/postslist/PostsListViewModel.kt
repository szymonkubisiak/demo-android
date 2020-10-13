package pl.kubisiak.demo.ui.postslist

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.demo.koinmodule.RootComponent
import pl.kubisiak.demo.ui.BaseViewModel
import pl.kubisiak.demo.ui.items.LoadingItemViewModel
import pl.kubisiak.demo.ui.items.PostItemViewModel


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

    private val postsForBlog = RootComponent.instance.sessionProvider().sesion.getBlogPosts2(blogID)

    init {
        disposer.add(postsForBlog.observable().subscribe { pager ->
            val retval = ObservableArrayList<BaseViewModel>()
            list = retval

            disposer.add(pager.nextPage().subscribe {
                list?.apply {
                    (lastOrNull() as? LoadingItemViewModel)?.also { remove(it) }
                    addAll(it.map { PostItemViewModel(it) })
                    add(LoadingItemViewModel(pager))
                }
            })
            subscribeLoader(pager.requestNextPage())//only the first page gets big loader
        })
        subscribeLoader(postsForBlog.ensure())
    }
}

