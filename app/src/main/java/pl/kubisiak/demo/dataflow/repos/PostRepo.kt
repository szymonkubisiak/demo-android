package pl.kubisiak.demo.dataflow.repos

import io.reactivex.Completable
import pl.kubisiak.demo.dataflow.BaseRepo
import pl.kubisiak.demo.dataflow.models.Post

class PostRepo(val id: Post.ID): BaseRepo<Post>() {
    override fun update(): Completable {
        //TODO tumblr client update post details
        return Completable.complete()
    }
}