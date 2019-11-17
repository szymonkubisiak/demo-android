package pl.kubisiak.demo.ui.listtest

import androidx.lifecycle.ViewModelProviders
import pl.kubisiak.demo.R
import pl.kubisiak.demo.ui.BaseFragment

class ListTestFragment : BaseFragment() {
    override fun createViewModel() =
        ViewModelProviders.of(this).get(ListTestViewModel::class.java)

    override fun getLayoutRes() =
        R.layout.listtest_fragment

    companion object {
        fun newInstance() = ListTestFragment()
    }
}