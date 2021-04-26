package pl.kubisiak.ui.main

import androidx.lifecycle.ViewModelProviders
import pl.kubisiak.ui.R
import pl.kubisiak.ui.BaseFragment

class MainFragment : BaseFragment() {
    override fun createViewModel() =
        ViewModelProviders.of(this).get(MainViewModel::class.java)

    override fun getLayoutRes() =
        R.layout.main_fragment

    companion object {
        fun newInstance() = MainFragment()
    }
}
