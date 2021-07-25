package pl.kubisiak.demo.dagger

import dagger.Component
import pl.kubisiak.data.dagger.JumblrModule
import pl.kubisiak.data.dagger.TumblrRetrofitModule
import pl.kubisiak.dataflow.dagger.DomainModule
import pl.kubisiak.demo.tumblr.ApplicationModule
import pl.kubisiak.ui.dagger.RootComponent
import pl.kubisiak.ui.dagger.UiModule
import javax.inject.Singleton


@Component(modules = [TumblrRetrofitModule::class, DomainModule::class, UiModule::class, ApplicationModule::class])
@Singleton
interface DaggerRoot
    : RootComponent

