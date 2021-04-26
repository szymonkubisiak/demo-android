package pl.kubisiak.ui.spinner

import androidx.annotation.LayoutRes
import pl.kubisiak.ui.BaseViewModel

interface DataTemplateSelector {
    @LayoutRes
    fun provideLayoutId(viewModel: BaseViewModel): Int
}