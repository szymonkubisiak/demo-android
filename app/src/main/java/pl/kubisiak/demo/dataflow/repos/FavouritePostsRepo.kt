package pl.kubisiak.demo.dataflow.repos

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.kubisiak.demo.dataflow.BaseRepo
import pl.kubisiak.demo.dataflow.models.FavouritePosts
import pl.kubisiak.demo.dataflow.models.Post
import java.util.concurrent.TimeUnit

class FavouritePostsRepo: BaseRepo<FavouritePosts>() {
    override fun update(): Completable {
        return Completable.complete()
    }

    fun tmpChangeState(newFavID: Post.ID?) {
        Completable
            .timer(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ postValue(FavouritePosts(newFavID))}
    }
}