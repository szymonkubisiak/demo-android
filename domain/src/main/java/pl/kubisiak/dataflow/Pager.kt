package pl.kubisiak.dataflow

import io.reactivex.Completable
import io.reactivex.Observable

interface Pager<T>: NextPageRequestor {
    fun nextPage(): Observable<T>
    override fun requestNextPage(): Completable
}

interface NextPageRequestor {
    fun requestNextPage(): Completable
}