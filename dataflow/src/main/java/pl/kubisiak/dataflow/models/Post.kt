package pl.kubisiak.dataflow.models

import pl.kubisiak.dataflow.BaseId
import java.io.Serializable

data class Post (val id: ID, val text: String, val rebloggedFrom: String?, val imageUrl: String? = null) {
    class ID internal constructor(private val _internal: Long): BaseId(), Serializable {
        override fun equals(other: Any?): Boolean {
            return if (other is ID) {
                _internal == other._internal
            } else {
                _internal == other
            }
        }

        internal fun rawValue() = _internal

        override fun hashCode(): Int {
            return _internal.toInt()
        }

        override fun toString(): String {
            return "Post ID: $_internal"
        }
    }
}