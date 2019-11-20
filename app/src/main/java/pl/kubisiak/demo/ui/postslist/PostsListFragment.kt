package pl.kubisiak.demo.ui.postslist

import androidx.lifecycle.ViewModelProviders
import pl.kubisiak.demo.R
import pl.kubisiak.demo.ui.BaseFragment

class PostsListFragment : BaseFragment() {
    override fun createViewModel() =
        ViewModelProviders.of(this).get(PostsListViewModel::class.java)

    override fun getLayoutRes() =
        R.layout.postslist_fragment

    companion object {
        fun newInstance() = PostsListFragment()
    }
}