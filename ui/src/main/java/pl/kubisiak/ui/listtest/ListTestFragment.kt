package pl.kubisiak.ui.listtest

import pl.kubisiak.ui.R
import pl.kubisiak.ui.BaseFragment

class ListTestFragment : BaseFragment() {
    override fun createViewModel() =
        getVmTroughProvider(::ListTestViewModel)

    override fun getLayoutRes() =
        R.layout.listtest_fragment

    companion object {
        fun newInstance() = ListTestFragment()
    }
}