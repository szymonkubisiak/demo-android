package pl.kubisiak.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.kubisiak.demo.ui.BaseFragment
import pl.kubisiak.demo.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance(), false)
        }
        MyApplication.getInstance().navigator.setActivity(this)
    }

    fun replaceFragment(newFragment: BaseFragment, addToBackstack: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, newFragment, newFragment.generateTag())
            .apply {
                if (addToBackstack) {
                    addToBackStack(null)
                    commitAllowingStateLoss()
                    supportFragmentManager.executePendingTransactions()
                }
                else
                    commitNow()
            }
    }

    fun goBack(): Boolean {
        return supportFragmentManager.popBackStackImmediate()
    }


    override fun onBackPressed() {
        if ((supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?)?.onBackPressed() == true) {
            return
        }
        super.onBackPressed()
    }
}
