package pl.kubisiak.demo.dataflow

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

abstract class BaseRepo<T> {

    protected val observable: BehaviorSubject<T> = BehaviorSubject.create()

    fun source(): Observable<T> =
        observable
            .distinctUntilChanged()
            .doOnSubscribe { ensure() }
            .observeOn(AndroidSchedulers.mainThread())

    fun ensure(): Completable? {
        if(observable.value == null) {
            return update()
        }
        return null
    }
    //TODO: add a mechanism to indicate request in progress and skip overlapping update()'s
    abstract fun update(): Completable

    fun postValue(newValue: T) {
        observable.onNext(newValue)
    }
}