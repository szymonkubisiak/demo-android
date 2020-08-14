package pl.kubisiak.demo

import android.app.Application
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.kubisiak.dataflow.returnScheduler

class MyApplication: Application() {

    var navigator: NavigatorImpl = NavigatorImpl()

    override fun onCreate() {
        super.onCreate()
        instance = this

        returnScheduler = AndroidSchedulers.mainThread()
    }

    companion object {
        private var instance: MyApplication? = null
        fun getInstance() = instance!!
    }
}