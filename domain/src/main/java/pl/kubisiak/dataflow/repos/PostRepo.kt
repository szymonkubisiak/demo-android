package pl.kubisiak.dataflow.repos

import io.reactivex.rxjava3.core.Observable
import pl.kubisiak.dataflow.models.Post

interface PostRepo {
    fun getPost(id: Post.ID): Observable<Post>
}
