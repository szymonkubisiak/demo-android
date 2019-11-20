package pl.kubisiak.demo.dataflow.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.kubisiak.demo.dataflow.BaseId

data class Blog(val id: ID, val name: String) {
    //TODO: constructor and _internal should be inaccessible to general audience
    @Parcelize
    class ID constructor(val _internal: String): BaseId(), Parcelable {
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

    }
}