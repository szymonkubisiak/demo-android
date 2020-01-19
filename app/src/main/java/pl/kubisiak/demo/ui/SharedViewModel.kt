package pl.kubisiak.demo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import java.lang.IllegalStateException
import java.util.concurrent.atomic.AtomicInteger

/**
 * SharedViewModel - a base class for view models that are shared between Activities
 * An instance is created when the first Activity or Fragment requests it
 * and cleared when the last holder gets destroyed
 */
abstract class SharedViewModel protected constructor() : ViewModel() {
    private val refCounter: AtomicInteger = AtomicInteger(0)

    final override fun onCleared() {
        release()
    }

    /**
     * onClearedActual - called when the instance is destroyed
     * override this method instead of the usual onCleared
     */
    protected open fun onClearedActual() {
        super.onCleared()
    }

    private fun retain(): Int {
        return refCounter.incrementAndGet()
    }

    private fun release(): Int {
        val counter: Int = refCounter.decrementAndGet()
        if (counter == 0) {
            synchronized(cacheLocker) {
                instanceCache.remove(javaClass)
            }
            onClearedActual()
        } else if (counter < 0) {
            throw IllegalStateException("SharedViewModel: reference count < 0")
        }
        return counter
    }

    class Factory : NewInstanceFactory() {
        override fun <T : ViewModel> create(klass: Class<T>): T {
            if (!SharedViewModel::class.java.isAssignableFrom(klass)) {
                return super.create(klass)
            }
            return klass.cast(getOrNew(klass))!!
        }

        private fun <T : ViewModel> getOrNew(klass: Class<T>): SharedViewModel {
            return synchronized(cacheLocker) {
                (instanceCache.get(klass) ?: createNew(klass).also {
                    instanceCache[klass] = it
                }).apply { retain() }
            }
        }

        private fun <T : ViewModel> createNew(klass: Class<T>): SharedViewModel {
            return klass.getConstructor().newInstance() as SharedViewModel
        }
    }

    companion object {
        private val instanceCache: MutableMap<Class<out ViewModel>, SharedViewModel> = HashMap()
        private val cacheLocker = Object()
        //TODO: test if locking is really necessary, as everything happens on UI threads
    }
}


