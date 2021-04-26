package pl.kubisiak.data.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.kubisiak.data.jumblr.JumblrWrapper
import pl.kubisiak.dataflow.BlogClient

@Module
interface JumblrModule {
    @Binds
    fun bindBlogClient(impl: JumblrWrapper): BlogClient

}

