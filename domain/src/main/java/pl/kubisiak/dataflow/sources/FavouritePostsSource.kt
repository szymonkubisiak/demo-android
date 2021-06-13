package pl.kubisiak.dataflow.sources

import io.reactivex.Completable
import io.reactivex.Scheduler
import pl.kubisiak.dataflow.BaseSource
import pl.kubisiak.dataflow.models.FavouritePosts
import pl.kubisiak.dataflow.models.Post
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FavouritePostsSource @Inject constructor(
    @Named("sourceReturn") private val returnScheduler: Scheduler,
) : BaseSource<FavouritePosts>() {
    override fun update(): Completable {
        return Completable.complete()
    }

    fun tmpChangeState(newFavID: Post.ID?) {
        Completable
            .timer(2, TimeUnit.SECONDS)
            .observeOn(returnScheduler)
            .subscribe { postValue(FavouritePosts(newFavID)) }
    }
}