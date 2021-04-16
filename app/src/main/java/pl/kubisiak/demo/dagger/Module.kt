package pl.kubisiak.demo.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import pl.kubisiak.dataflow.BlogClient
import pl.kubisiak.dataflow.Session
import pl.kubisiak.dataflow.createSession
import pl.kubisiak.demo.tumblr.newClient
import javax.inject.Inject
import javax.inject.Singleton

@Component(modules = [ClientProvider::class])
@Singleton
interface RootComponent {
    fun sessionProvider(): SessionProvider

    companion object {
        val instance: RootComponent = DaggerRootComponent.create()
    }
}

@Module
class ClientProvider @Inject constructor() {
    @Provides
    fun createClient(): BlogClient {
        return newClient()
    }
}

@Singleton
class SessionProvider @Inject constructor(private val client: BlogClient) {
    val sesion: Session = createSession(client)

    init {
        counter ++
    }
    companion object {
        var counter = 0
    }
}

