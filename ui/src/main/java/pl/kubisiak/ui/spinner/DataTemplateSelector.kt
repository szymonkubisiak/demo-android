package pl.kubisiak.ui.spinner

import androidx.annotation.LayoutRes
import pl.kubisiak.ui.BaseSubViewModel

interface DataTemplateSelector {
    @LayoutRes
    fun provideLayoutId(viewModel: BaseSubViewModel): Int
}