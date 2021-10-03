package pl.kubisiak.ui.listtest

import pl.kubisiak.ui.R
import pl.kubisiak.ui.BaseFragment
import pl.kubisiak.ui.dagger.RootComponent

class ListTestFragment : BaseFragment() {
    override fun createViewModel() =
        getVmTroughProvider { ListTestViewModel(RootComponent.instance.navigator()) }

    override fun getLayoutRes() =
        R.layout.listtest_fragment

    companion object {
        fun newInstance() = ListTestFragment()
    }
}