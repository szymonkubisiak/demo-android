package pl.kubisiak.dataflow.models

import pl.kubisiak.dataflow.BaseId
import pl.kubisiak.dataflow.Identifiable
import java.io.Serializable

data class Post (override val id: ID, val text: String, val rebloggedFrom: String?, val imageUrl: String? = null) : Identifiable<Post.ID> {
    class ID private constructor(private val _internal: Long, val owner: Blog.ID): BaseId(), Serializable {
        override fun equals(other: Any?): Boolean =
            other is ID && this.javaClass == other.javaClass && this._internal == other._internal

        override fun hashCode(): Int {
            return _internal.toInt()
        }

        override fun toString(): String {
            return "Post ID: $_internal"
        }

        fun rawValue() = _internal

        companion object {
            fun create(value: Long, owner: Blog.ID): ID {
                return ID(value, owner)
            }
        }
    }
}