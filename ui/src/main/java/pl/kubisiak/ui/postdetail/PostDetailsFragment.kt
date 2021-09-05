package pl.kubisiak.ui.postdetail

import android.os.Bundle
import pl.kubisiak.ui.R
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.ui.BaseFragment
import pl.kubisiak.ui.BaseViewModel

private const val POST_ID = "postidparam"

class PostDetailsFragment : BaseFragment() {

    override fun createViewModel(): BaseViewModel {
        val id = arguments?.getSerializable(POST_ID) as Post.ID
        val retval = getVmTroughProvider(id, ::PostDetailsViewModel)
        return retval
    }

    override fun getLayoutRes() =
        R.layout.postdetails_fragment

    companion object {
        fun newInstance(id: Post.ID) = PostDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(POST_ID, id)
            }
        }
    }
}