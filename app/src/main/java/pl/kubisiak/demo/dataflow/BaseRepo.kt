package pl.kubisiak.demo.dataflow

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class BaseRepo<T> {

    private val observable: Observable<T> = BehaviorSubject.create()

    fun source() = observable
}