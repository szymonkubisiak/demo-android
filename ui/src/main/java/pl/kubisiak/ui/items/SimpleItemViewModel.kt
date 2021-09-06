package pl.kubisiak.ui.items

import androidx.databinding.Bindable
import io.reactivex.internal.disposables.DisposableContainer

import pl.kubisiak.ui.BaseSubViewModel

class SimpleItemViewModel constructor(disposer: DisposableContainer, @Bindable val text : String) : BaseSubViewModel(disposer)