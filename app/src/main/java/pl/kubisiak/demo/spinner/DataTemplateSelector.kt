package pl.kubisiak.demo.spinner

import androidx.annotation.LayoutRes
import pl.kubisiak.demo.ui.BaseViewModel

interface DataTemplateSelector {
    @LayoutRes
    fun provideLayoutId(viewModel: BaseViewModel): Int
}