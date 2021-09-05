package pl.kubisiak.ui.main

import pl.kubisiak.ui.R
import pl.kubisiak.ui.BaseFragment

class MainFragment : BaseFragment() {
    override fun createViewModel() =
        getVmTroughProvider(::MainViewModel)

    override fun getLayoutRes() =
        R.layout.main_fragment

    companion object {
        fun newInstance() = MainFragment()
    }
}
