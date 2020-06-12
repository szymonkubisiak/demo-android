package pl.kubisiak.dataflow.sources

import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import pl.kubisiak.dataflow.BaseSource
import pl.kubisiak.dataflow.Identifiable
import pl.kubisiak.dataflow.SourceGroup
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.returnScheduler
import kotlin.collections.ArrayList

internal class PostsForBlogSource(private val group: SourceGroup, override val id: Blog.ID): Identifiable<Blog.ID>, BaseSource<List<Post.ID>>() {
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
            
            group.client.getPostsForBlog(id, null, null)
                .observeOn(returnScheduler)
                .doOnNext(::processIncoming)
                .ignoreElements()
                .subscribe(subject)

            return subject
        }
    }

    //take a blob of data from client and split it into appropriate buckets
    private fun processIncoming(posts: List<Post>) {
        val retval = ArrayList<Post.ID>()
        for (postModel in posts) {
            val postUC = group.posts[postModel.id]
            postUC.postValue(postModel)
            retval.add(postModel.id)
        }
        postValue(retval)
    }
}