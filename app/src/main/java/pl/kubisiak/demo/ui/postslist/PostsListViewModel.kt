package pl.kubisiak.demo.ui.postslist

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import org.koin.core.KoinComponent
import org.koin.core.inject
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.demo.dataflow.RepoGroup
import pl.kubisiak.demo.dataflow.models.Blog
import pl.kubisiak.demo.ui.BaseViewModel
import pl.kubisiak.demo.ui.items.PostItemViewModel


class PostsListViewModel(val blogID: Blog.ID): BaseViewModel(), KoinComponent {

    private var _list: ObservableList<BaseViewModel>? = ObservableArrayList<BaseViewModel>()
    var list: ObservableList<BaseViewModel>?
        @Bindable get() = _list
        @Bindable set(value) {
            _list = value
            notifyPropertyChanged(BR.list)
        }

    private var _isLoading: Boolean = false
    var isLoading: Boolean
        @Bindable get() = _isLoading
        @Bindable set(value) {
            _isLoading = value
            notifyPropertyChanged(BR.loading)
        }

    fun forceRefresh() {
        isLoading = true
        disposer.add(postsForBlog.update().subscribe(
            { isLoading = false },
            { isLoading = false }))
    }

    private val group:RepoGroup by inject()
    private val postsForBlog = group.blogs[blogID]

    init {
        disposer.add(postsForBlog.source().subscribe {
            val retval = ObservableArrayList<BaseViewModel>()
                .apply {
                    for (onePostId in it)
                        add(PostItemViewModel(onePostId))
                }
            list = retval
        })
        isLoading = true
        disposer.add(postsForBlog.ensure()?.subscribe(
            { isLoading = false },
            { isLoading = false }))
    }
}

