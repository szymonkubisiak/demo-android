package pl.kubisiak.demo.ui.postdetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import pl.kubisiak.demo.R
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.demo.ui.BaseFragment

private const val POST_ID = "postidparam"

class PostDetailsFragment : BaseFragment() {
    private class Factory(val id: Post.ID) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.cast(PostDetailsViewModel(id))!!
        }
    }

    override fun createViewModel() =
        ViewModelProviders
            .of(this, Factory(arguments?.getSerializable(POST_ID) as Post.ID))
            .get(PostDetailsViewModel::class.java)

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