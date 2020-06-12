package pl.kubisiak.dataflow

import io.reactivex.Completable
import io.reactivex.Observable

interface Source<T> {

    fun observable(): Observable<T>
    fun ensure(): Completable?
    fun update(): Completable

}