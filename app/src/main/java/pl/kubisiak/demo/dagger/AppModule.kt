package pl.kubisiak.demo.dagger

import dagger.Component
import pl.kubisiak.data.dagger.JumblrModule
import pl.kubisiak.dataflow.BlogClient
import pl.kubisiak.dataflow.Session
import pl.kubisiak.dataflow.createSession
import pl.kubisiak.dataflow.dagger.DomainModule
import pl.kubisiak.dataflow.dagger.SessionProvider
import pl.kubisiak.demo.tumblr.ApplicationModule
import pl.kubisiak.ui.dagger.RootComponent
import pl.kubisiak.ui.dagger.UiModule
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class SessionProviderImpl @Inject constructor(private val client: BlogClient): SessionProvider {
    override val sesion: Session = createSession(client)
}

@Component(modules = [JumblrModule::class, DomainModule::class, UiModule::class, ApplicationModule::class])
@Singleton
interface DaggerRoot
    : RootComponent

