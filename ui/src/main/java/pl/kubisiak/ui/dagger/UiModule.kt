package pl.kubisiak.ui.dagger

import dagger.Module
import dagger.Provides
import pl.kubisiak.dataflow.Session
import pl.kubisiak.ui.Navigator
import pl.kubisiak.ui.NavigatorImpl
import javax.inject.Singleton

@Singleton
interface RootComponent {

    fun session(): Session

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