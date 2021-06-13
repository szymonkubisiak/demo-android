package pl.kubisiak.ui.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.kubisiak.dataflow.*
import pl.kubisiak.dataflow.models.*
import pl.kubisiak.dataflow.sources.FavouritePostsSource
import pl.kubisiak.ui.Navigator
import pl.kubisiak.ui.NavigatorImpl
import javax.inject.Named
import javax.inject.Singleton

@Singleton
interface RootComponent {

    fun postDepot(): Depot<Post.ID, Source<Post>>
    fun postsFroBlogPaginatedDepot(): Depot<Blog.ID, Source<Pager<List<Post.ID>>>>
    fun favourites(): FavouritePostsSource

    fun navigator(): Navigator

    companion object {
        lateinit var instance: RootComponent
    }
}

@Module
class UiModule {
    @Provides
    @Singleton
    fun provideNavigator(): Navigator = nav
    val nav = NavigatorImpl()

    @Provides
    @Named("sourceReturn")
    fun provideReturnScheduler() = AndroidSchedulers.mainThread()
}