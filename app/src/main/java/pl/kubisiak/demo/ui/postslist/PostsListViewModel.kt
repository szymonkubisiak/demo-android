package pl.kubisiak.demo.ui.postslist

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import org.koin.core.KoinComponent
import org.koin.core.inject
import pl.kubisiak.demo.BR
import pl.kubisiak.demo.dataflow.RepoGroup
import pl.kubisiak.demo.dataflow.models.Blog
import pl.kubisiak.demo.ui.BaseViewModel
import pl.kubisiak.demo.ui.items.PostItemViewModel


class PostsListViewModel : BaseViewModel(), KoinComponent {

    private var _list: ObservableList<BaseViewModel>? = ObservableArrayList<BaseViewModel>()
    var list: ObservableList<BaseViewModel>?
        @Bindable get() = _list
        @Bindable set(value) {
            _list= value
            notifyPropertyChanged(BR.list)
        }

    private val group:RepoGroup by inject()

    init {
        val tmpBlogID: Blog.ID = Blog.ID("seejohnrun.tumblr.com")
        val postsForBlog = group.blogs[tmpBlogID]
        disposer.add(postsForBlog.source().subscribe {
            val retval = ObservableArrayList<BaseViewModel>()
                .apply {
                    for (onePostId in it)
                        add(PostItemViewModel(onePostId))
                }
            list = retval
        })
    }
}

