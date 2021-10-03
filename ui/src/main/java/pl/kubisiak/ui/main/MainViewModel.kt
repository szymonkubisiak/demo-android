package pl.kubisiak.ui.main

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.internal.disposables.DisposableContainer
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.ui.BaseSubViewModel
import pl.kubisiak.ui.BaseViewModel
import pl.kubisiak.ui.Navigator
import javax.inject.Inject

class MainViewModel @Inject constructor(navigator: Navigator) : BaseViewModel(navigator) {

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

    private var _list: ObservableList<BaseSubViewModel>? = ObservableArrayList<BaseSubViewModel>()
    var list: ObservableList<BaseSubViewModel>?
        @Bindable get() = _list
        @Bindable set(value) {
            _list = value
            notifyPropertyChanged(BR.list)
        }

    private var _currentItem: BaseSubViewModel? = null
    var currentItem: BaseSubViewModel?
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
                SpinnerItem(disposer, "avtavr"),
                SpinnerItem(disposer, "nasa"),
                SpinnerItem(disposer, "dankmemeuniversity"),
                //SpinnerItem("artsdrug", disposer),
                SpinnerItem(disposer, "supermodelcats")
            )
        )
    }

    class SpinnerItem(disposer: DisposableContainer, val _handle: String) : BaseSubViewModel(disposer) {
        val handle: String?
            @Bindable get() {
                return _handle
            }
    }
}
