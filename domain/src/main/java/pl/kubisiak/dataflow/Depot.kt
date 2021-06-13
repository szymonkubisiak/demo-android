package pl.kubisiak.dataflow

interface Depot<I, out T> {
    fun get(id: I): T
}
