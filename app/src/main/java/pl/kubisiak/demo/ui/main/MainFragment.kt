package pl.kubisiak.demo.ui.main

import androidx.lifecycle.ViewModelProviders
import pl.kubisiak.demo.R
import pl.kubisiak.demo.ui.BaseFragment

class MainFragment : BaseFragment() {
    override fun createViewModel() =
        ViewModelProviders.of(this).get(MainViewModel::class.java)

    override fun getLayoutRes() =
        R.layout.main_fragment

    companion object {
        fun newInstance() = MainFragment()
    }
}
