package pl.kubisiak.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.ui.dagger.RootComponent

abstract class BaseFragment: Fragment() {

    private var viewModel: BaseViewModel? = null

    abstract fun createViewModel(): BaseViewModel
    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflateAndBind( inflater, container )
    }

    private fun inflateAndBind(inflater: LayoutInflater, container: ViewGroup?): View {
        if(viewModel == null )
            viewModel = createViewModel()
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.vm, viewModel)
        binding.executePendingBindings()
        return binding.root
    }

    open fun onBackPressed() = RootComponent.instance.navigator().goBack()

    open fun generateTag(): String {
        return javaClass.canonicalName!!
    }
}