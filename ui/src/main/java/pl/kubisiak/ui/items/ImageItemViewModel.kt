package pl.kubisiak.ui.items

import androidx.databinding.Bindable
import io.reactivex.internal.disposables.DisposableContainer
import pl.kubisiak.ui.BaseSubViewModel

class ImageItemViewModel(disposer: DisposableContainer, @Bindable val title: String, @Bindable val imageurl: String? = null) : BaseSubViewModel(disposer)