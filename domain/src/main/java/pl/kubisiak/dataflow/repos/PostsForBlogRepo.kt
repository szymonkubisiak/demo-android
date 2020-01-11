package pl.kubisiak.dataflow.repos

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.CompletableSubject
import pl.kubisiak.dataflow.BaseRepo
import pl.kubisiak.dataflow.RepoGroup
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import kotlin.collections.ArrayList

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
        //take blob of data from client and split it into appropriate buckets
        val posts = group.client.getPostsForBlogSync(id)
        val retval = ArrayList<Post.ID>()
        for (postModel in posts) {
            val postRepo = group.posts[postModel.id]
            postRepo.postValue(postModel)
            retval.add(postModel.id)
        }
        postValue(retval)
    }
}