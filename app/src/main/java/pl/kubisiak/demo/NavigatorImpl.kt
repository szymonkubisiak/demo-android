package pl.kubisiak.demo

import android.widget.Toast
import pl.kubisiak.demo.dataflow.models.Blog
import pl.kubisiak.demo.ui.Navigator
import pl.kubisiak.demo.ui.listtest.ListTestFragment
import pl.kubisiak.demo.ui.main.MainFragment
import pl.kubisiak.demo.ui.postslist.PostsListFragment
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

    override fun goToFirstScreen() {
        getActivity()?.replaceFragment(MainFragment.newInstance())
    }

    override fun debugToast(text: CharSequence, long: Boolean) {
        if(BuildConfig.DEBUG) {
            Toast
                .makeText(MyApplication.getInstance(), text, if(long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
                .show()
        }
    }
}