package pl.kubisiak.dataflow.sources

import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import pl.kubisiak.dataflow.*
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import kotlin.collections.ArrayList

class PostsForBlogSource(override val id: Blog.ID): Identifiable<Blog.ID>, BaseSource<List<Post.ID>>() {
    private var ongoingUpdate: Completable? = null

    private val client: BlogClient = get()

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
            
            client.getPostsForBlog(id, null, null)
                .observeOn(get())
                .doOnNext(::processIncoming)
                .ignoreElements()
                .subscribe(subject)

            return subject
        }
    }

    //take a blob of data from client and split it into appropriate buckets
    internal fun processIncoming(posts: List<Post>) {
        val retval = ArrayList<Post.ID>()
        for (postModel in posts) {
            val postUC: PostSource = get { parametersOf(postModel.id) }
            postUC.processIncoming(postModel)
            retval.add(postModel.id)
        }
        postValue(retval)
    }
}