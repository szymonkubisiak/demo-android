package pl.kubisiak.demo.dataflow

import org.koin.dsl.module
import pl.kubisiak.dataflow.createSession
import pl.kubisiak.demo.tumblr.newClient

val reposModule = module {
    single { createSession(newClient()) }
}
