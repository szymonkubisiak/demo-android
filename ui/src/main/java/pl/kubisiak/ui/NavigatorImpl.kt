package pl.kubisiak.ui

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.ui.listtest.ListTestFragment
import pl.kubisiak.ui.main.MainFragment
import pl.kubisiak.ui.postdetail.PostDetailsFragment
import pl.kubisiak.ui.postslist.PostsListFragment
import java.lang.ref.WeakReference

class NavigatorImpl : Navigator {

    private var _activity = WeakReference<MainActivity>(null)

    fun setActivity(currentActivity: MainActivity) {
        _activity = WeakReference(currentActivity)
    }

    private fun getActivity(): MainActivity?
        = _activity.get()

    override fun goBack(): Boolean =
        getActivity()?.goBack() ?: false

    override fun goToListTest() {
        getActivity()?.replaceFragment(ListTestFragment.newInstance())
    }

    override fun goToPostsList(id: Blog.ID) {
        getActivity()?.replaceFragment(PostsListFragment.newInstance(id))
    }

    override fun goToPostDetail(id: Post.ID) {
        getActivity()?.replaceFragment(PostDetailsFragment.newInstance(id))
    }

    override fun goToFirstScreen() {
        getActivity()?.replaceFragment(MainFragment.newInstance())
    }

    override fun debugToast(text: CharSequence, long: Boolean) {
        if(BuildConfig.DEBUG) {
            Toast
                .makeText(getActivity(), text, if(long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun showMessage(text: String, title: String?) {
        getActivity()?.also {
            val alertDialog = AlertDialog.Builder(it) //set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton("OK") { _, _ ->

                }
//                .setNegativeButton("No") { _, _ ->
//
//                }
                .show()
        }
    }
}