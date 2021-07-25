package pl.kubisiak.ui.postslist

import android.os.Bundle
import pl.kubisiak.ui.R
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.ui.BaseFragment

private const val BLOG_ID = "blogidparam"

class PostsListFragment : BaseFragment() {

    override fun createViewModel() =
        getVmTroughProvider(arguments?.getSerializable(BLOG_ID) as Blog.ID, ::PostsListViewModel)

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