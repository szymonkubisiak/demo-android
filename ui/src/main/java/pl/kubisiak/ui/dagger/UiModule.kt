package pl.kubisiak.ui.dagger

import dagger.Module
import dagger.Provides
import pl.kubisiak.dataflow.dagger.SessionProvider
import pl.kubisiak.ui.Navigator
import pl.kubisiak.ui.NavigatorImpl
import javax.inject.Singleton

@Singleton
interface RootComponent {

    fun sessionProvider(): SessionProvider

    fun navigator(): Navigator

    companion object {
        lateinit var instance: RootComponent
    }
}

@Module
class UiModule {
    @Provides
    fun provideNavigator(): Navigator = nav
    val nav = NavigatorImpl()
}