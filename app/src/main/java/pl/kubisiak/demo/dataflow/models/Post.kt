package pl.kubisiak.demo.dataflow.models

import pl.kubisiak.demo.dataflow.BaseId

data class Post (val id: ID, val text: String, val imageUrl: String? = null) {
    //TODO: constructor and _internal should be inaccessible to general audience
    public class ID constructor(val _internal: Long): BaseId() {
        override fun equals(other: Any?): Boolean {
            return if (other is ID) {
                _internal == other._internal
            } else {
                _internal == other
            }
        }
        override fun hashCode(): Int {
            return _internal.toInt()
        }

        override fun toString(): String {
            return "Post ID: $_internal"
        }
    }
}