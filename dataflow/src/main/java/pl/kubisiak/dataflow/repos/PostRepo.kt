package pl.kubisiak.dataflow.repos

import io.reactivex.Completable
import pl.kubisiak.dataflow.BaseRepo
import pl.kubisiak.dataflow.models.Post

class PostRepo(val id: Post.ID): BaseRepo<Post>() {
    override fun update(): Completable {
        //TODO tumblr client update post details
        return Completable.complete()
    }
}