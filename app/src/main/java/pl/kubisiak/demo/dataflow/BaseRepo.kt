package pl.kubisiak.demo.dataflow

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

    fun ensure() {
        if(observable.value == null) {
            update()
        }
    }
    //TODO: add a mechanism to indicate request in progress and skip overlapping update()'s
    abstract fun update()

    fun postValue(newValue: T) {
        observable.onNext(newValue)
    }
}