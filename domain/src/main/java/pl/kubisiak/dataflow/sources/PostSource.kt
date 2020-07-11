package pl.kubisiak.dataflow.sources

import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import org.koin.core.get
import pl.kubisiak.dataflow.*
import pl.kubisiak.dataflow.models.Post

class PostSource(override val id: Post.ID): Identifiable<Post.ID>, BaseSource<Post>() {
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

            client.getPost(id)
                .observeOn(get())
                .doOnNext(::processIncoming)
                .ignoreElements()
                .subscribe(subject)

            return subject
        }
    }

    internal fun processIncoming(post: Post) {
        postValue(post)
    }
}