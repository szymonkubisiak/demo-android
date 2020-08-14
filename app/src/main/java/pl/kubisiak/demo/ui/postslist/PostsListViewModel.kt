package pl.kubisiak.demo.ui.postslist

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.demo.koinmodule.RootComponent
import pl.kubisiak.demo.ui.BaseViewModel
import pl.kubisiak.demo.ui.items.PostItemViewModel


class PostsListViewModel(val blogID: Blog.ID): BaseViewModel() {

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

    private val postsForBlog = RootComponent.instance.sessionProvider().sesion.getBlogPosts(blogID)

    init {
        disposer.add(postsForBlog.observable().subscribe {
            val retval = ObservableArrayList<BaseViewModel>()
                .apply {
                    for (onePostId in it)
                        add(PostItemViewModel(onePostId))
                }
            list = retval
        })
        subscribeLoader(postsForBlog.ensure())
    }
}

