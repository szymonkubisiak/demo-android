package pl.kubisiak.dataflow.sources

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.PublishSubject
import pl.kubisiak.dataflow.*
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import kotlin.collections.ArrayList

internal class PostsForBlogPaginatedSource(
    private val repo: PostsForBlogRepo,
    private val postsDepot: Depot<Post.ID, PostSource>,
    override val id: Blog.ID
) : Identifiable<Blog.ID>, BaseSource<Pager<List<Post.ID>>>() {
    override fun update(): Completable {
        synchronized(this) {
            val retval = PostForBlogPager(repo, postsDepot, id)
            postValue(retval)
            return retval.requestNextPage()
        }
    }
}

internal class PostForBlogPager(
    private val repo: PostsForBlogRepo,
    private val postsDepot: Depot<Post.ID, PostSource>,
    override val id: Blog.ID
) : Identifiable<Blog.ID>, BasePager<List<Post.ID>>() {
    private var ongoingUpdate: Completable? = null
    private var offset: Int = 0

    override fun requestNextPage(): Completable {
        synchronized(this) {
            ongoingUpdate?.also {
                return it
            }

            val subject = CompletableSubject.create()
            ongoingUpdate = subject
            subject.subscribe(
                { synchronized(this) { ongoingUpdate = null } },
                { synchronized(this) { ongoingUpdate = null } })

            repo.getPostsForBlog(id, offset, 5)
                .observeOn(returnScheduler)
                .doOnNext(::processIncoming)
                .ignoreElements()
                .subscribe(subject)

            return subject
        }
    }

    //take a blob of data from client and split it into appropriate buckets
    private fun processIncoming(posts: List<Post>) {
        offset += posts.size
        val retval = ArrayList<Post.ID>()
        for (postModel in posts) {
            val postUC = postsDepot.get(postModel.id)
            postUC.postValue(postModel)
            retval.add(postModel.id)
        }
        postValue(retval)
    }
}

abstract class BasePager<T> : Pager<T> {

    protected val observable: PublishSubject<T> = PublishSubject.create()

    override fun nextPage(): Observable<T> =
        observable
            .distinctUntilChanged()
    //.observeOn(AndroidSchedulers.mainThread())

    fun postValue(newValue: T) {
        observable.onNext(newValue)
    }
}