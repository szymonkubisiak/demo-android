package pl.kubisiak.dataflow.models

import pl.kubisiak.dataflow.BaseId
import java.io.Serializable

data class Blog(val id: ID, val name: String) {
    //TODO: constructor and _internal should be inaccessible to general audience
    class ID constructor(private val _internal: String): BaseId(), Serializable {
        override fun equals(other: Any?): Boolean {
            return if (other is ID) {
                _internal == other._internal
            } else {
                _internal == other
            }
        }
        override fun hashCode(): Int {
            return _internal.hashCode()
        }

        override fun toString(): String {
            return "Blog ID: $_internal"
        }

        fun rawValue() = _internal
    }
}