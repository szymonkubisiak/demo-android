package pl.kubisiak.dataflow

import io.reactivex.Observable
import io.reactivex.Scheduler
import pl.kubisiak.dataflow.models.*
import pl.kubisiak.dataflow.sources.*
import javax.inject.Inject
import javax.inject.Singleton

interface Session {
    fun getPost(id: Post.ID): Source<Post>
    fun getBlogPosts(id: Blog.ID): Source<List<Post.ID>>
    fun getBlogPosts2(id: Blog.ID): Source<Pager<List<Post.ID>>>
    fun getFavouritePosts(): Source<FavouritePosts>
    fun markPostAsFavourite(id: Post.ID?)
}

@Singleton
class SourceGroup @Inject constructor(val client: BlogClient) : Session {
    internal val posts = DistinctFactory<Post.ID, PostSource> { PostSource(this, it) }
    internal val blogs2 = DistinctFactory<Blog.ID, PostsForBlogPaginatedSource> { PostsForBlogPaginatedSource(this, it) }
    internal val blogs = DistinctFactory<Blog.ID, PostsForBlogSource> { PostsForBlogSource(this, it) }
    internal val favouritePosts = FavouritePostsSource(this)

    override fun getPost(id: Post.ID): Source<Post> = posts[id]
    override fun getBlogPosts(id: Blog.ID): Source<List<Post.ID>> = blogs[id]
    override fun getBlogPosts2(id: Blog.ID): Source<Pager<List<Post.ID>>> = blogs2[id]
    override fun getFavouritePosts(): Source<FavouritePosts> = favouritePosts
    override fun markPostAsFavourite(id: Post.ID?) = favouritePosts.tmpChangeState(id)
}

var returnScheduler: Scheduler? = null

interface BlogClient {
    fun getPostsForBlog(id: Blog.ID, offset: Int?, limit: Int?): Observable<List<Post>>
    fun getPost(id: Post.ID): Observable<Post>
}
