package pl.kubisiak.demo.ui.listtest

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import pl.kubisiak.demo.BR
import pl.kubisiak.demo.ui.BaseViewModel
import pl.kubisiak.demo.ui.items.ImageItemViewModel
import pl.kubisiak.demo.ui.items.SimpleItemViewModel

class ListTestViewModel : BaseViewModel() {

    private var _list: ObservableList<BaseViewModel>? = ObservableArrayList<BaseViewModel>()
        .apply {
            add(SimpleItemViewModel("first"))
            add(ImageItemViewModel("raz", "https://66.media.tumblr.com/55e4e80a8e647d0a87451ecbf686daf7/tumblr_pz3kqorVxS1wzrxl4o4_1280.jpg"))
            add(ImageItemViewModel("dwa", "https://66.media.tumblr.com/e5c9a569c848d6314327ad04e8344c70/tumblr_pz3kqorVxS1wzrxl4o5_1280.jpg"))
            add(SimpleItemViewModel("second"))
            add(SimpleItemViewModel("third"))
            add(ImageItemViewModel("trzy", "https://66.media.tumblr.com/297944800ab0d789c43e75ad00fa0197/tumblr_pz3kqorVxS1wzrxl4o6_1280.jpg"))
            add(ImageItemViewModel("cztery", "https://66.media.tumblr.com/9668628dcfed5aca6584b77d3b776f20/tumblr_pz3kqorVxS1wzrxl4o7_1280.jpg"))
            add(ImageItemViewModel("pięć", "https://66.media.tumblr.com/a6ea710620dc9d6eac89b5db68683e08/tumblr_pz3kqorVxS1wzrxl4o8_1280.jpg"))
        }
    var list: ObservableList<BaseViewModel>?
        @Bindable get() = _list
        @Bindable set(value) {
            _list= value
            notifyPropertyChanged(BR.list)
        }
}
