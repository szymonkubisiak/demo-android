package pl.kubisiak.demo.ui

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.DisposableContainer
import org.koin.core.KoinComponent
import pl.kubisiak.demo.MyApplication

open class BaseViewModel: Observable, ViewModel(), KoinComponent {
    val navigator: Navigator = MyApplication.getInstance().navigator

    private val _disposer = CompositeDisposable()

    val disposer:DisposableContainer = _disposer

    override fun onCleared() {
        super.onCleared()
        _disposer.dispose()
    }

    protected var _isLoading: Boolean = false
    var isLoading: Boolean
        @Bindable get() = _isLoading
        @Bindable set(value) {
            _isLoading = value
            notifyPropertyChanged(BR.loading)
        }

    protected fun subscribeLoader(p: Completable?) {
        p?.also {
            isLoading = true
            disposer.add(
                it.subscribe(
                    { isLoading = false },
                    { isLoading = false })
            )
        }
    }

    //from BaseObservable:
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
}