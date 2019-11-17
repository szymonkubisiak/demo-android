package pl.kubisiak.demo

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null
        fun getInstance() = instance
    }
}