package pl.kubisiak.dataflow.sources

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.CompletableSubject
import pl.kubisiak.dataflow.*
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.repos.PostsForBlogRepo
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlin.collections.ArrayList

internal class PostsForBlogSource @AssistedInject constructor(
    private val repo: PostsForBlogRepo,
    private val postsDepot: Depot<Post.ID, PostSource>,
    @Named("sourceReturn") private val returnScheduler: Scheduler,
    @Assisted override val id: Blog.ID
) : Identifiable<Blog.ID>, BaseSource<List<Post.ID>>() {
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

            repo.getPostsForBlog(id, null, null)
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
            val postUC = postsDepot.get(postModel.id)
            postUC.postValue(postModel)
            retval.add(postModel.id)
        }
        postValue(retval)
    }
}

@AssistedFactory
internal interface PostsForBlogAssistedFactory {
    fun get(id: Blog.ID): PostsForBlogSource
}

@Singleton
internal class PostsForBlogDepot @Inject constructor(
    factory: PostsForBlogAssistedFactory
) : DistinctFactory<Blog.ID, PostsForBlogSource>(factory::get)