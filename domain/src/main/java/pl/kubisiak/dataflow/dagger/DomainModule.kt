package pl.kubisiak.dataflow.dagger

import dagger.Binds
import dagger.Module
import pl.kubisiak.dataflow.Session
import pl.kubisiak.dataflow.SourceGroup

@Module
interface DomainModule {
    @Binds
    fun bindSession(impl: SourceGroup): Session
}
