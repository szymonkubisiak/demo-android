package pl.kubisiak.demo.ui.listtest

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import pl.kubisiak.demo.BR
import pl.kubisiak.demo.ui.BaseViewModel

class ListTestViewModel : BaseViewModel() {

    private var _list: ObservableList<BaseViewModel>? = ObservableArrayList<BaseViewModel>()
        .apply {
            add(SimpleItemViewModel("first"))
            add(SimpleItemViewModel("second"))
            add(SimpleItemViewModel("third"))
        }
    var list: ObservableList<BaseViewModel>?
        @Bindable get() = _list
        @Bindable set(value) {
            _list= value
            notifyPropertyChanged(BR.list)
        }
}

class SimpleItemViewModel(@Bindable val text : String) : BaseViewModel()