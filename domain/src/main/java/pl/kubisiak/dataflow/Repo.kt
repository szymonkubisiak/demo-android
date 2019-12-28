package pl.kubisiak.dataflow

import io.reactivex.Completable
import io.reactivex.Observable

interface Repo<T> {

    fun source(): Observable<T>
    fun ensure(): Completable?
    fun update(): Completable

}