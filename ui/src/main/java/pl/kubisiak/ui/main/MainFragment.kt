package pl.kubisiak.ui.main

import pl.kubisiak.ui.R
import pl.kubisiak.ui.BaseFragment
import pl.kubisiak.ui.dagger.RootComponent

class MainFragment : BaseFragment() {
    override fun createViewModel() =
        getVmTroughProvider { RootComponent.instance.mainViewModel() }

    override fun getLayoutRes() =
        R.layout.main_fragment

    companion object {
        fun newInstance() = MainFragment()
    }
}
