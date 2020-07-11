package pl.kubisiak.demo.koinmodule

import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.dsl.module
import pl.kubisiak.dataflow.BlogClient
import pl.kubisiak.demo.tumblr.newClient

val sessionModule = module {
    single<BlogClient> { newClient() }
    single { AndroidSchedulers.mainThread() }
}
