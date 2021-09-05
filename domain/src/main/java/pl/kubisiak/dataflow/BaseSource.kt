package pl.kubisiak.dataflow

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class BaseSource<T : Any> : Source<T> {

    protected val observable: BehaviorSubject<T> = BehaviorSubject.create()

    override fun observable(): Observable<T> =
        observable
            .distinctUntilChanged()
            //.observeOn(AndroidSchedulers.mainThread())

    override fun ensure(): Completable? {
        if(observable.value == null) {
            return update()
        }
        return null
    }
    //TODO: add a mechanism to indicate request in progress and skip overlapping update()'s
    abstract override fun update(): Completable

    fun postValue(newValue: T) {
        observable.onNext(newValue)
    }
}