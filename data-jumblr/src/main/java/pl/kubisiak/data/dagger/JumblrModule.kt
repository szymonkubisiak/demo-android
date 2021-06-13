package pl.kubisiak.data.dagger

import dagger.Binds
import dagger.Module
import pl.kubisiak.data.jumblr.JumblrWrapper
import pl.kubisiak.dataflow.repos.PostRepo
import pl.kubisiak.dataflow.repos.PostsForBlogRepo

@Module
interface JumblrModule {
    @Binds
    fun bindPostsRepo(impl: JumblrWrapper): PostRepo

    @Binds
    fun bindPostsForBlogRepo(impl: JumblrWrapper): PostsForBlogRepo
}

