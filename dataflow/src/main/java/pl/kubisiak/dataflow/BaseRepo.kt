package pl.kubisiak.dataflow

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent

abstract class BaseRepo<T> : Repo<T>, KoinComponent {

    protected val observable: BehaviorSubject<T> = BehaviorSubject.create()

    override fun source(): Observable<T> =
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