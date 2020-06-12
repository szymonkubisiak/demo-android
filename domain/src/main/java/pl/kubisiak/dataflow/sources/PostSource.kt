package pl.kubisiak.dataflow.sources

import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import pl.kubisiak.dataflow.BaseSource
import pl.kubisiak.dataflow.Identifiable
import pl.kubisiak.dataflow.SourceGroup
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.returnScheduler

internal class PostSource(private val group: SourceGroup, override val id: Post.ID): Identifiable<Post.ID>, BaseSource<Post>() {
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