package pl.kubisiak.demo.dataflow

import org.koin.dsl.module
import pl.kubisiak.dataflow.RepoGroup
import pl.kubisiak.demo.tumblr.newClient

val reposModule = module {
    single { RepoGroup() }
    single { newClient() }
}
