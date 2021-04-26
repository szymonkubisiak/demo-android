package pl.kubisiak.dataflow.dagger

import dagger.Module
import pl.kubisiak.dataflow.Session

@Module
interface DomainModule {
}

interface SessionProvider {
    val sesion: Session
}