package pl.kubisiak.dataflow.sources

import io.reactivex.Completable
import pl.kubisiak.dataflow.BaseSource
import pl.kubisiak.dataflow.models.FavouritePosts
import pl.kubisiak.dataflow.models.Post
import java.util.concurrent.TimeUnit

class FavouritePostsSource: BaseSource<FavouritePosts>() {
    override fun update(): Completable {
        return Completable.complete()
    }

    fun markPostAsFavourite(newFavID: Post.ID?) {
        Completable
            .timer(2, TimeUnit.SECONDS)
            .subscribe{ postValue(FavouritePosts(newFavID))}
    }
}