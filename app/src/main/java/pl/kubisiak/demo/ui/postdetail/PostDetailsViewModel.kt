package pl.kubisiak.demo.ui.postdetail


import android.text.Html
import android.text.Spanned
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import org.koin.core.inject
import pl.kubisiak.dataflow.Session
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.demo.ui.BaseViewModel


class PostDetailsViewModel(val id: Post.ID): BaseViewModel() {

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

    private var _model: Post? = null
    var model: Post?
        @Bindable get() = _model
        set(value) {
            _model = value
            notifyPropertyChanged(BR.model)
        }

    fun goToSourceBlog() {
        model?.rebloggedFrom?.also {
            navigator.goToPostsList(Blog.ID.create("$it.tumblr.com"))
        }
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

    private val group: Session by inject()
    private val repo = group.getPost(id)

    init {
        disposer.add(repo.source().subscribe {
            model = it
            title = it.text
        })
        val favRepo = group.getFavouritePosts()
        disposer.add(favRepo.source().subscribe {
            favourite = id == it.favPostid
        })
    }
}