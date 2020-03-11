package pl.kubisiak.data.jumblr

import com.tumblr.jumblr.JumblrClient
import com.tumblr.jumblr.types.AnswerPost
import com.tumblr.jumblr.types.ChatPost
import com.tumblr.jumblr.types.PhotoPost
import com.tumblr.jumblr.types.TextPost
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import pl.kubisiak.dataflow.BlogClient
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class JumblrWrapper(consumerKey: String, consumerSecret: String) : BlogClient {

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
            val id = Post.ID.create(post.id)
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
                else -> continue@postsLoop
            }
            retval.add(postModel)
        }
        return(retval)
    }

    override fun getPostsForBlog(id: Blog.ID, offset: Int?, limit: Int?): Observable<List<Post>> {
        return Observable
            .fromCallable { getPostsForBlogSync(id, offset, limit) }
            .subscribeOn(Schedulers.io())
    }
}