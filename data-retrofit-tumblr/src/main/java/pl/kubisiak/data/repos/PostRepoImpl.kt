package pl.kubisiak.data.repos

import io.reactivex.Observable
import pl.kubisiak.data.dto.PojoPost
import pl.kubisiak.data.tumblr.TumblrClientKey
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.repos.PostRepo
import javax.inject.Inject

class PostRepoImpl @Inject constructor(
    private val http: pl.kubisiak.data.tumblr.Post,
    private val apiKey: TumblrClientKey
    ) : PostRepo {

    override fun getPost(id: Post.ID): Observable<Post> {
        return http
            .getPost(id.owner.rawValue(), id.rawValue(), apiKey.consumerKey)
            .map { post ->
                mapper_post(post, id)!!
            }
            .toObservable()
    }

    companion object {
        fun mapper_post(
            post: PojoPost,
            id: Post.ID
        ): Post? {
            val postModel = when (post) {
                is PojoPost.Photo -> Post(
                    id,
                    post.caption ?: "NO CAPTION",
                    post.rebloggedFromName,
                    post.photos?.firstOrNull()?.sizes?.firstOrNull()?.url
                )
                is PojoPost.Text -> Post(id, post.title + " " + post.body, post.rebloggedFromName)
                is PojoPost.Answer -> Post(id, post.question + " " + post.answer, post.rebloggedFromName)
                is PojoPost.Chat -> Post(id, post.title + " " + post.body, post.rebloggedFromName)
                else -> null
            }
            return postModel
        }
    }
}