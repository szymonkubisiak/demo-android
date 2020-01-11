package pl.kubisiak.demo.ui.postslist

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import pl.kubisiak.demo.R
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.demo.ui.BaseFragment

private const val BLOG_ID = "blogidparam"

class PostsListFragment : BaseFragment() {
    private class Factory(val id: Blog.ID) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.cast(PostsListViewModel(id))!!
        }
    }

    override fun createViewModel() =
        ViewModelProviders
            .of(this, Factory(arguments?.getSerializable(BLOG_ID) as Blog.ID))
            .get(PostsListViewModel::class.java)

    override fun getLayoutRes() =
        R.layout.postslist_fragment

    companion object {
        fun newInstance(id: Blog.ID) = PostsListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(BLOG_ID, id)
            }
        }
    }
}