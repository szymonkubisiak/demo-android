package pl.kubisiak.dataflow.repos

import io.reactivex.Completable
import pl.kubisiak.dataflow.BaseRepo
import pl.kubisiak.dataflow.Identifiable
import pl.kubisiak.dataflow.RepoGroup
import pl.kubisiak.dataflow.models.Post

internal class PostRepo(private val group: RepoGroup, override val id: Post.ID): Identifiable<Post.ID>, BaseRepo<Post>() {
    override fun update(): Completable {
        //TODO tumblr client update post details
        return Completable.complete()
    }
}