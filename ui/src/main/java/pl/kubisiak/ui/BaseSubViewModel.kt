package pl.kubisiak.ui

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.PropertyChangeRegistry
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import pl.kubisiak.ui.dagger.RootComponent
import java.util.concurrent.atomic.AtomicLong

/**
 * Base for sub-viewmodels - like viewmodels, but their lifetime is not managed by the Android framework.
 * This means no more onCleared, so the disposer must come in as a ctor param.
 */
open class BaseSubViewModel protected constructor(val disposer: DisposableContainer) : Observable {
    val navigator: Navigator = RootComponent.instance.navigator()

    protected operator fun DisposableContainer.plusAssign(subscription: Disposable) {
        if (!this.add(subscription))
            throw IllegalStateException("Container already disposed")
    }

    //region from BaseObservable:
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    open fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }
    //endregion

    companion object {
        val uniqueIdGenerator = AtomicLong(0)
    }
}