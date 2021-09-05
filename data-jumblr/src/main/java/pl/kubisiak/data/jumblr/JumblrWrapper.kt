package pl.kubisiak.data.jumblr

import com.tumblr.jumblr.JumblrClient
import com.tumblr.jumblr.types.AnswerPost
import com.tumblr.jumblr.types.ChatPost
import com.tumblr.jumblr.types.PhotoPost
import com.tumblr.jumblr.types.TextPost
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.kubisiak.dataflow.*
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.repos.PostRepo
import pl.kubisiak.dataflow.repos.PostsForBlogRepo
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Singleton
class JumblrWrapper(consumerKey: String, consumerSecret: String) : PostsForBlogRepo, PostRepo {

    @Inject
    public constructor (config: TumblrClientKey) : this(config.consumerKey, config.consumerKey)

    private val client = JumblrClient(consumerKey, consumerSecret)

    private fun getPostsForBlogSync(blogid: Blog.ID, offset: Int?, limit: Int?): List<Post> {
        val options = HashMap<String, Any?>()
        options["reblog_info"] = "true"

        limit?.also { options["limit"] = it }
        offset?.also { options["offset"] = it }

        val blog = client.blogInfo(blogid.rawValue())
        val posts: List<com.tumblr.jumblr.types.Post> = blog.posts(options)

        val retval = ArrayList<Post>()
        postsLoop@ for (post in posts) {
            val id = Post.ID.create(post.id, blogid)
            try {
                val postModel = mapper_post(post, id)
                retval.add(postModel)
            } catch (x: Exception) {
                continue@postsLoop
            }
        }
        return (retval)
    }

    override fun getPostsForBlog(id: Blog.ID, offset: Int?, limit: Int?): Observable<List<Post>> {
        return Observable
            .fromCallable { getPostsForBlogSync(id, offset, limit) }
            .subscribeOn(Schedulers.io())
    }

    private fun getPostSync(id: Post.ID): Post {
        val blogid = id.owner
        val blog = client.blogInfo(blogid.rawValue())
        val post: com.tumblr.jumblr.types.Post = blog.getPost(id.rawValue())
        val postModel = mapper_post(post, id)
        return (postModel)
    }

    override fun getPost(id: Post.ID): Observable<Post> {
        return Observable
            .fromCallable { getPostSync(id) }
            .subscribeOn(Schedulers.io())
    }

    companion object {
        private fun mapper_post(
            post: com.tumblr.jumblr.types.Post,
            id: Post.ID
        ): Post {
            val postModel = when (post) {
                is PhotoPost -> Post(
                    id,
                    post.caption,
                    post.rebloggedFromName,
                    post.photos?.firstOrNull()?.sizes?.firstOrNull()?.url
                )
                is TextPost -> Post(id, post.title + " " + post.body, post.rebloggedFromName)
                is AnswerPost -> Post(id, post.question + " " + post.answer, post.rebloggedFromName)
                is ChatPost -> Post(id, post.title + " " + post.body, post.rebloggedFromName)
                else -> throw Exception("unsupported post type")//continue@postsLoop
            }
            return postModel
        }
    }
}