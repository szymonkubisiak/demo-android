package pl.kubisiak.ui.items

import androidx.databinding.Bindable
import pl.kubisiak.ui.BaseViewModel

data class ImageItemViewModel(@Bindable val title: String, @Bindable val imageurl: String? = null) : BaseViewModel()