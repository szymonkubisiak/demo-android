package pl.kubisiak.demo.ui.postdetail


import android.text.Html
import android.text.Spanned
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import org.koin.core.inject
import pl.kubisiak.demo.dataflow.RepoGroup
import pl.kubisiak.demo.dataflow.models.Blog
import pl.kubisiak.demo.dataflow.models.Post
import pl.kubisiak.demo.ui.BaseViewModel


class PostDetailsViewModel(id: Post.ID): BaseViewModel() {

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
            navigator.goToPostsList(Blog.ID("$it.tumblr.com"))
        }
    }

    private val group: RepoGroup by inject()
    private val repo = group.posts[id]

    init {
        disposer.add( repo.source().subscribe {
            model = it
            title = it.text
        } )
    }
}