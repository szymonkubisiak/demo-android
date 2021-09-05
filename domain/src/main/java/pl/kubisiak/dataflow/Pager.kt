package pl.kubisiak.dataflow

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface Pager<T : Any> : NextPageRequestor {
    fun nextPage(): Observable<T>
    override fun requestNextPage(): Completable
}

interface NextPageRequestor {
    fun requestNextPage(): Completable
}