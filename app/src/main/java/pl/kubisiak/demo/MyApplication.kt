package pl.kubisiak.demo

import android.app.Application
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.kubisiak.dataflow.returnScheduler
import pl.kubisiak.demo.dagger.DaggerDaggerRoot
import pl.kubisiak.ui.dagger.RootComponent


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        returnScheduler = AndroidSchedulers.mainThread()
        RootComponent.instance = DaggerDaggerRoot.create()

    }

    companion object {
        private var instance: MyApplication? = null
        fun getInstance() = instance!!
    }
}