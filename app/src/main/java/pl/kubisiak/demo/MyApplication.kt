package pl.kubisiak.demo

import android.app.Application

class MyApplication: Application() {

    var navigator: NavigatorImpl = NavigatorImpl()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null
        fun getInstance() = instance!!
    }
}