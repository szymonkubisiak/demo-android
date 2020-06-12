package pl.kubisiak.demo

import android.app.Application
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import pl.kubisiak.dataflow.returnScheduler
import pl.kubisiak.demo.koinmodule.sessionModule

class MyApplication: Application() {

    var navigator: NavigatorImpl = NavigatorImpl()

    override fun onCreate() {
        super.onCreate()
        instance = this

        returnScheduler = AndroidSchedulers.mainThread()

        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(sessionModule)
        }
    }

    companion object {
        private var instance: MyApplication? = null
        fun getInstance() = instance!!
    }
}