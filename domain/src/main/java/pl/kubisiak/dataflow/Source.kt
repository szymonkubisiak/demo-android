package pl.kubisiak.dataflow

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface Source<T : Any> {

    fun observable(): Observable<T>
    fun ensure(): Completable?
    fun update(): Completable

//    fun ensureAndObserve(updateObserver: CompletableObserver): Observable<T> {
//        val update = ensure()
//        if (update != null)
//            update.subscribeWith(updateObserver)
//        else
//            updateObserver.onComplete()
//        return observable()
//    }
}