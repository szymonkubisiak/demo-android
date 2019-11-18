package pl.kubisiak.demo.ui.items

import androidx.databinding.Bindable
import pl.kubisiak.demo.ui.BaseViewModel

data class ImageItemViewModel(@Bindable val title: String, @Bindable val imageurl: String? = null) : BaseViewModel()