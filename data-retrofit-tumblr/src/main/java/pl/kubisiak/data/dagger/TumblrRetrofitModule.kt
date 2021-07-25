package pl.kubisiak.data.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.kubisiak.data.repos.PostRepoImpl
import pl.kubisiak.data.repos.PostsForBlogRepoImpl
import pl.kubisiak.data.tumblr.Post
import pl.kubisiak.data.tumblr.PostsForBlog
import pl.kubisiak.data.tumblr.RetrofitProvider
import pl.kubisiak.dataflow.repos.PostRepo
import pl.kubisiak.dataflow.repos.PostsForBlogRepo

@Module(includes = [HttpModule::class])
interface TumblrRetrofitModule {
    @Binds
    fun bindPostsRepo(impl: PostRepoImpl): PostRepo

    @Binds
    fun bindPostsForBlogRepo(impl: PostsForBlogRepoImpl): PostsForBlogRepo
}


@Module
class HttpModule {

    @Provides
    fun createPostService(retrofitProvider: RetrofitProvider): Post {
        return retrofitProvider.provide().create(Post::class.java)
    }

    @Provides
    fun createPostsForBlogService(retrofitProvider: RetrofitProvider): PostsForBlog {
        return retrofitProvider.provide().create(PostsForBlog::class.java)
    }
}