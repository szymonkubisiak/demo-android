package pl.kubisiak.dataflow

interface Identifiable<I : BaseId> {
    val id:I
}