package pl.kubisiak.data.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.kubisiak.data.jumblr.JumblrWrapper
import pl.kubisiak.dataflow.BlogClient
import pl.kubisiak.dataflow.PostRepo
import pl.kubisiak.dataflow.PostsForBlogRepo

@Module
interface JumblrModule {
    @Binds
    fun bindBlogClient(impl: JumblrWrapper): BlogClient

    @Binds
    fun bindPostsRepo(impl: JumblrWrapper): PostRepo

    @Binds
    fun bindPostsForBlogRepo(impl: JumblrWrapper): PostsForBlogRepo
}

