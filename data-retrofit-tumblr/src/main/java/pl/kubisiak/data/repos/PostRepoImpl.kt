package pl.kubisiak.data.repos

import io.reactivex.rxjava3.core.Observable
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.repos.PostRepo
import javax.inject.Inject

class PostRepoImpl @Inject constructor(val http: pl.kubisiak.data.tumblr.Post) : PostRepo {

    override fun getPost(id: Post.ID): Observable<Post> {
        return http
            .getPost(id.owner.rawValue(), id.rawValue())
            .map {  Post(id, "dummy", null) }
            .toObservable()
    }
}