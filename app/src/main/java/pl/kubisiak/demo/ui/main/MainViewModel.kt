package pl.kubisiak.demo.ui.main

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
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
        navigator.goToPostsList(Blog.ID.create(title + ".tumblr.com"))
    }

    private var _list: ObservableList<BaseViewModel>? = ObservableArrayList<BaseViewModel>()
    var list: ObservableList<BaseViewModel>?
        @Bindable get() = _list
        @Bindable set(value) {
            _list = value
            notifyPropertyChanged(BR.list)
        }

    private var _currentItem: BaseViewModel? = null
    var currentItem: BaseViewModel?
        @Bindable get() = _currentItem
        @Bindable set(value) {
            _currentItem = value
            notifyPropertyChanged(BR.currentItem)
        }

    fun onPostsFromSpinnerClicked() {
        val postName = (currentItem as? SpinnerItem)?.handle ?: return
        navigator.goToPostsList(Blog.ID.create(postName + ".tumblr.com"))
    }

    init {
        list?.addAll(
            listOf(
                SpinnerItem("nasa"),
                SpinnerItem("dankmemeuniversity"),
                SpinnerItem("artsdrug"),
                SpinnerItem("supermodelcats")
            )
        )
    }

    class SpinnerItem(val _handle: String) : BaseViewModel() {
        val handle: String?
            @Bindable get() {
                return _handle
            }
    }
}
