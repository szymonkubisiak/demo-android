package pl.kubisiak.dataflow.models

import pl.kubisiak.dataflow.BaseId
import pl.kubisiak.dataflow.Identifiable
import java.io.Serializable

data class Blog(override val id: ID, val name: String) : Identifiable<Blog.ID> {
    //TODO: constructor and _internal should be inaccessible to general audience
    class ID private constructor(private val _internal: String): BaseId(), Serializable {
        override fun equals(other: Any?): Boolean =
            other is ID && this.javaClass == other.javaClass && this._internal == other._internal

        override fun hashCode(): Int {
            return _internal.hashCode()
        }

        override fun toString(): String {
            return "Blog ID: $_internal"
        }

        fun rawValue() = _internal

        companion object {
            fun create(value: String): ID {
                return ID(value)
            }
        }
    }
}