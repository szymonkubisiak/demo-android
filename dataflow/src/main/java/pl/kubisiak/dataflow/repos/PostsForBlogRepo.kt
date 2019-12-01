package pl.kubisiak.dataflow.repos

import com.tumblr.jumblr.JumblrClient
import com.tumblr.jumblr.types.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.CompletableSubject
import pl.kubisiak.dataflow.BaseRepo
import pl.kubisiak.dataflow.RepoGroup
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

internal class PostsForBlogRepo(private val group: RepoGroup, val id: Blog.ID) : BaseRepo<List<Post.ID>>() {
    private var ongoingUpdate: Completable? = null

    override fun update(): Completable {
        synchronized(this) {
            ongoingUpdate?.also {
                return it
            }

            val subject = CompletableSubject.create()
            ongoingUpdate = subject
            subject.subscribe(
                { synchronized(this) { ongoingUpdate = null } },
                { synchronized(this) { ongoingUpdate = null } })

            Completable
                .fromAction { executeUpdate() }
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(subject)
            return subject
        }
    }

    private fun executeUpdate() {
        //TODO: abstract the Jumblr-related code away to remove dependency
        val client = group.client
        lateinit var posts: List<com.tumblr.jumblr.types.Post>

        val options = HashMap<String, Any?>()
        options.put("reblog_info", "true")

        val blog = client.blogInfo(id.rawValue())
        posts = blog.posts(options)

        val retval = ArrayList<Post.ID>()
        postsLoop@ for (post in posts) {
            val id = Post.ID(post.id)
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
            val postRepo = group.posts[id]
            postRepo.postValue(postModel)
            retval.add(id)
        }
        postValue(retval)
    }
}