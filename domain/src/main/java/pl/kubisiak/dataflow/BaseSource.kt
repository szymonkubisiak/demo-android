package pl.kubisiak.dataflow

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.get

abstract class BaseSource<T> : Source<T>, KoinComponent {

    protected val observable: BehaviorSubject<T> = BehaviorSubject.create()

    override fun observable(): Observable<T> =
        observable
            .distinctUntilChanged()
            .observeOn(get())

    override fun ensure(): Completable? {
        if(observable.value == null) {
            return update()
        }
        return null
    }
    //TODO: add a mechanism to indicate request in progress and skip overlapping update()'s
    abstract override fun update(): Completable

    protected fun postValue(newValue: T) {
        observable.onNext(newValue)
    }
}