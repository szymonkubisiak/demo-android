package pl.kubisiak.demo.ui

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel

open class BaseViewModel(private val baseObservable:BaseObservable = BaseObservable() ): Observable by baseObservable, ViewModel() {

    fun notifyPropertyChanged(fieldId: Int) {
        baseObservable.notifyPropertyChanged(fieldId)
    }
}