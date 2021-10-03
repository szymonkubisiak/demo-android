package pl.kubisiak.ui.postdetail


import android.text.Html
import android.text.Spanned
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.ui.dagger.RootComponent
import pl.kubisiak.ui.BaseViewModel
import pl.kubisiak.ui.Navigator


class PostDetailsViewModel @AssistedInject constructor(
    @Assisted val id: Post.ID,
    navigator: Navigator,
) : BaseViewModel(navigator) {

    @AssistedFactory
    interface Factory {
        fun get(id: Post.ID): PostDetailsViewModel
    }

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
        favSource.tmpChangeState(if (favourite) id else null)
    }

    private val favSource = RootComponent.instance.favourites()
    private val source = RootComponent.instance.postDepot().get(id)

    init {
        disposer.add(source.observable().subscribe {
            model = it
            title = it.text
        })
        disposer.add(favSource.observable().subscribe {
            favourite = id == it.favPostid
        })
    }
}