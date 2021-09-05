package pl.kubisiak.data.repos

import io.reactivex.rxjava3.core.Observable
import pl.kubisiak.data.dto.*

import pl.kubisiak.data.tumblr.TumblrClientKey
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.repos.PostsForBlogRepo
import javax.inject.Inject

class PostsForBlogRepoImpl @Inject constructor(
    private val http: pl.kubisiak.data.tumblr.PostsForBlog,
    private val apiKey: TumblrClientKey,
    ) : PostsForBlogRepo {

    override fun getPostsForBlog(id: Blog.ID, offset: Int?, limit: Int?): Observable<List<Post>> {
        return http
            .getPostsForBlog(id.rawValue(), apiKey.consumerKey, offset, limit)
            .map { resp ->
                resp.response!!.posts!!.mapNotNull { post ->
                    //Post(Post.ID.create(it.id!!, id), "dummy", null)

                    mapper_post(post, Post.ID.create(post.id!!, id))

                }
            }
            .toObservable()
    }

    private fun mapper_post(
        post: PojoPost,
        id: Post.ID
    ): Post? {
        val postModel = when (post) {
            is PojoPost.Photo -> Post(
                id,
                post.caption?:"NO CAPTION",
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