package pl.kubisiak.demo.ui.items

import android.text.Html
import android.text.Spanned
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.dataflow.NextPageRequestor
import pl.kubisiak.demo.ui.BaseViewModel

class LoadingItemViewModel(val pager: NextPageRequestor): BaseViewModel() {

    private var _title: String? = "Loading..."
    var title: String?
        @Bindable get() {
            pager.requestNextPage()
            return _title
        }
        set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
            notifyPropertyChanged(BR.formattedTitle)
        }

    @Bindable
    fun getFormattedTitle(): Spanned? {
        return title?.let { Html.fromHtml(it) }
    }

}