package pl.kubisiak.dataflow.sources

import io.reactivex.Completable
import pl.kubisiak.dataflow.BaseSource
import pl.kubisiak.dataflow.SourceGroup
import pl.kubisiak.dataflow.models.FavouritePosts
import pl.kubisiak.dataflow.models.Post
import java.util.concurrent.TimeUnit

internal class FavouritePostsSource(private val group: SourceGroup): BaseSource<FavouritePosts>() {
    override fun update(): Completable {
        return Completable.complete()
    }

    fun tmpChangeState(newFavID: Post.ID?) {
        Completable
            .timer(2, TimeUnit.SECONDS)
            //.observeOn(AndroidSchedulers.mainThread())
            .subscribe{ postValue(FavouritePosts(newFavID))}
    }
}