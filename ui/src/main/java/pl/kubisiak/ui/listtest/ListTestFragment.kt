package pl.kubisiak.ui.listtest

import androidx.lifecycle.ViewModelProviders
import pl.kubisiak.ui.R
import pl.kubisiak.ui.BaseFragment

class ListTestFragment : BaseFragment() {
    override fun createViewModel() =
        ViewModelProviders.of(this).get(ListTestViewModel::class.java)

    override fun getLayoutRes() =
        R.layout.listtest_fragment

    companion object {
        fun newInstance() = ListTestFragment()
    }
}