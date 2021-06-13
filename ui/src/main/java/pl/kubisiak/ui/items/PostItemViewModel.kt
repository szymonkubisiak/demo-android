package pl.kubisiak.ui.items

import android.text.Html
import android.text.Spanned
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.ui.dagger.RootComponent
import pl.kubisiak.ui.BaseViewModel

class PostItemViewModel(val id: Post.ID) : BaseViewModel() {

    private var _title: String? = null
    var title: String?
        @Bindable get() = _title
        set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
            notifyPropertyChanged(BR.formattedTitle)
        }

    @Bindable
    fun getFormattedTitle(): Spanned? {
        return _title?.let { Html.fromHtml(it) }
    }

    fun goToDetails() {
        navigator.goToPostDetail(id)
    }

    private var _imageurl: String? = null
    var imageurl: String?
        @Bindable get() = _imageurl
        set(value) {
            _imageurl = value
            notifyPropertyChanged(BR.imageurl)
        }

    private var _isFavourite: Boolean = false
    var favourite: Boolean
        @Bindable get() = _isFavourite
        set(value) {
            _isFavourite = value
            notifyPropertyChanged(BR.favourite)
        }

    fun makeFavourite(favourite: Boolean) {
        favSource.tmpChangeState(if (favourite) id else null)
    }

    val favSource = RootComponent.instance.favourites()

    init {
        val source = RootComponent.instance.postDepot().get(id)
        disposer.add(source.observable().subscribe {
            title = it.text
            imageurl = it.imageUrl
        })

        disposer.add(favSource.observable().subscribe {
            favourite = id == it.favPostid
        })
    }
}