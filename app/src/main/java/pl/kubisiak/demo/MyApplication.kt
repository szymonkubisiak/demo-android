package pl.kubisiak.demo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import pl.kubisiak.demo.koinmodule.reposModule

class MyApplication: Application() {

    var navigator: NavigatorImpl = NavigatorImpl()

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(reposModule)
        }
    }

    companion object {
        private var instance: MyApplication? = null
        fun getInstance() = instance!!
    }
}