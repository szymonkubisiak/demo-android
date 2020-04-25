package pl.kubisiak.dataflow.repos

import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import pl.kubisiak.dataflow.BaseRepo
import pl.kubisiak.dataflow.Identifiable
import pl.kubisiak.dataflow.RepoGroup
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.returnScheduler

internal class PostRepo(private val group: RepoGroup, override val id: Post.ID): Identifiable<Post.ID>, BaseRepo<Post>() {
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

            group.client.getPost(id)
                .observeOn(returnScheduler)
                .doOnNext(::processIncoming)
                .ignoreElements()
                .subscribe(subject)

            return subject
        }
    }

    private fun processIncoming(post: Post) {
        postValue(post)
    }
}