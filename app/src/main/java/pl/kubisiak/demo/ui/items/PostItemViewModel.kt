package pl.kubisiak.demo.ui.items

import android.text.Html
import android.text.Spanned
import androidx.databinding.Bindable
import org.koin.core.inject
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.dataflow.Session
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.demo.ui.BaseViewModel

class PostItemViewModel (val id: Post.ID): BaseViewModel() {

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
        group.markPostAsFavourite(if(favourite) id else null)
    }

    private val group:Session by inject()

    init {
        val source = group.getPost(id)
        disposer.add( source.observable().subscribe {
            title = it.text
            imageurl = it.imageUrl
        } )
        val favSource = group.getFavouritePosts()
        disposer.add( favSource.observable().subscribe {
            favourite = id == it.favPostid
        } )
    }
}