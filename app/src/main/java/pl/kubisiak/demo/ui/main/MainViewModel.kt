package pl.kubisiak.demo.ui.main

import android.widget.Toast
import androidx.databinding.Bindable
import pl.kubisiak.demo.BR
import pl.kubisiak.demo.MyApplication
import pl.kubisiak.demo.ui.BaseViewModel

class MainViewModel : BaseViewModel() {

    private var _title: String? = "Foo"
    var title: String?
        @Bindable get() = _title
        @Bindable set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
        }

    fun onTouchMeClicked() {
        Toast.makeText(MyApplication.getInstance(), "bar!", Toast.LENGTH_SHORT).show()
    }
}
