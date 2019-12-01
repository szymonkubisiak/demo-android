package pl.kubisiak.dataflow.repos

import io.reactivex.Completable
import pl.kubisiak.dataflow.BaseRepo
import pl.kubisiak.dataflow.RepoGroup
import pl.kubisiak.dataflow.models.FavouritePosts
import pl.kubisiak.dataflow.models.Post
import java.util.concurrent.TimeUnit

internal class FavouritePostsRepo(private val group: RepoGroup): BaseRepo<FavouritePosts>() {
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