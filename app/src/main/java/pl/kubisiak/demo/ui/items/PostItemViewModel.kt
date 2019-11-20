package pl.kubisiak.demo.ui.items

import android.text.Html
import android.text.Spanned
import androidx.databinding.Bindable
import org.koin.core.inject
import androidx.databinding.library.baseAdapters.BR
import pl.kubisiak.demo.dataflow.RepoGroup
import pl.kubisiak.demo.dataflow.models.Post
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


    private var _imageurl: String? = null
    var imageurl: String?
        @Bindable get() = _imageurl
        set(value) {
            _imageurl = value
            notifyPropertyChanged(BR.imageurl)
        }

    private val group:RepoGroup by inject()

    init {
        val repo = group.posts[id]
        disposer.add( repo.source().subscribe {
            title = it.text
            imageurl = it.imageUrl
        } )
    }
}