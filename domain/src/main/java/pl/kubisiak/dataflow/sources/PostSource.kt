package pl.kubisiak.dataflow.sources

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.CompletableSubject
import pl.kubisiak.dataflow.*
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.repos.PostRepo
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

internal class PostSource @AssistedInject constructor(
    private val repo: PostRepo,
    @Named("sourceReturn") private val returnScheduler: Scheduler,
    @Assisted override val id: Post.ID
) : Identifiable<Post.ID>, BaseSource<Post>() {
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

            repo.getPost(id)
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

@AssistedFactory
internal interface PostsAssistedFactory {
    fun get(id: Post.ID): PostSource
}

@Singleton
internal class PostsDepot @Inject constructor(
    factory: PostsAssistedFactory
) : DistinctFactory<Post.ID, PostSource>(factory::get)