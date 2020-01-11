package pl.kubisiak.demo.ui.main

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.demo.ui.BaseViewModel

class MainViewModel : BaseViewModel() {

    private var _title: String? = "seejohnrun"
    var title: String?
        @Bindable get() = _title
        @Bindable set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
        }

    fun onTouchMeClicked() {
        navigator.goToListTest()
    }

    fun onPostsListClicked() {
        navigator.goToPostsList(Blog.ID(title + ".tumblr.com"))
    }

}
