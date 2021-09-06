package pl.kubisiak.ui.listtest

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.ui.BaseSubViewModel
import pl.kubisiak.ui.BaseViewModel
import pl.kubisiak.ui.items.ImageItemViewModel
import pl.kubisiak.ui.items.SimpleItemViewModel

class ListTestViewModel : BaseViewModel() {

    private var _list: ObservableList<BaseSubViewModel>? = ObservableArrayList<BaseSubViewModel>()
        .apply {
            add(SimpleItemViewModel(disposer, "first"))
            add(ImageItemViewModel(disposer,"raz", "https://66.media.tumblr.com/55e4e80a8e647d0a87451ecbf686daf7/tumblr_pz3kqorVxS1wzrxl4o4_1280.jpg"))
            add(ImageItemViewModel(disposer,"dwa", "https://66.media.tumblr.com/e5c9a569c848d6314327ad04e8344c70/tumblr_pz3kqorVxS1wzrxl4o5_1280.jpg"))
            add(SimpleItemViewModel(disposer,"second"))
            add(SimpleItemViewModel(disposer,"third"))
            add(ImageItemViewModel(disposer,"trzy", "https://66.media.tumblr.com/297944800ab0d789c43e75ad00fa0197/tumblr_pz3kqorVxS1wzrxl4o6_1280.jpg"))
            add(ImageItemViewModel(disposer,"cztery", "https://66.media.tumblr.com/9668628dcfed5aca6584b77d3b776f20/tumblr_pz3kqorVxS1wzrxl4o7_1280.jpg"))
            add(ImageItemViewModel(disposer,"pięć", "https://66.media.tumblr.com/a6ea710620dc9d6eac89b5db68683e08/tumblr_pz3kqorVxS1wzrxl4o8_1280.jpg"))
        }
    var list: ObservableList<BaseSubViewModel>?
        @Bindable get() = _list
        @Bindable set(value) {
            _list= value
            notifyPropertyChanged(BR.list)
        }
}
