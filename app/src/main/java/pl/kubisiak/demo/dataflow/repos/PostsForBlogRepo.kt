package pl.kubisiak.demo.dataflow.repos

import com.tumblr.jumblr.JumblrClient
import com.tumblr.jumblr.exceptions.JumblrException
import com.tumblr.jumblr.types.*
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import pl.kubisiak.demo.dataflow.BaseRepo
import pl.kubisiak.demo.dataflow.RepoGroup
import pl.kubisiak.demo.dataflow.models.Blog
import pl.kubisiak.demo.dataflow.models.Post
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PostsForBlogRepo(val id: Blog.ID): BaseRepo<List<Post.ID>>(), KoinComponent {
    private val group: RepoGroup by inject()

    var ongoingUpdate: Completable? = null

    override fun update(): Completable {
        synchronized(this) {
            ongoingUpdate?.also {
                return it
            }

            val comp = CompletableSubject.create()
            ongoingUpdate = comp
            comp.subscribe(
                { synchronized(this) { ongoingUpdate = null } },
                { synchronized(this) { ongoingUpdate = null } })

        Thread {
            //TODO: abstract the Jumblr-related code away to remove dependency
            val client: JumblrClient by inject()
            lateinit var posts: List<com.tumblr.jumblr.types.Post>
            try {
                val options = HashMap<String, Any?>()
                options.put("reblog_info", "true")

                val blog = client.blogInfo(id._internal)
                posts = blog.posts(options)
            }
            catch (x: JumblrException) {
                comp.onError(x)
                return@Thread
            }

            val retval = ArrayList<Post.ID>()
            postsLoop@ for(post in posts) {
                val id = Post.ID(post.id)
                val postModel = when (post) {
                    is PhotoPost -> Post(id, post.caption, post.photos?.firstOrNull()?.sizes?.firstOrNull()?.url)
                    is TextPost -> Post(id, post.title + " " + post.body)
                    is AnswerPost -> Post(id, post.question + " " + post.answer)
                    is ChatPost -> Post(id, post.title + " " + post.body)
                    else -> continue@postsLoop
                }
                val postRepo = group.posts[id]
                postRepo.postValue(postModel)
                retval.add(id)
            }
            postValue(retval)
            comp.onComplete()
        }.start()

            return comp
        }
    }
}