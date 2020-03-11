package pl.kubisiak.dataflow

import io.reactivex.Observable
import io.reactivex.Scheduler
import pl.kubisiak.dataflow.models.*
import pl.kubisiak.dataflow.repos.*

interface Session {
    fun getPost(id: Post.ID): Repo<Post>
    fun getBlogPosts(id: Blog.ID): Repo<List<Post.ID>>
    fun getFavouritePosts(): Repo<FavouritePosts>
    fun markPostAsFavourite(id: Post.ID?)
}

internal class RepoGroup(val client: BlogClient) : Session {
    internal val posts = DistinctFactory<Post.ID, PostRepo> { PostRepo(this, it) }
    internal val blogs = DistinctFactory<Blog.ID, PostsForBlogRepo> { PostsForBlogRepo(this, it) }
    internal val favouritePosts = FavouritePostsRepo(this)

    override fun getPost(id: Post.ID): Repo<Post> = posts[id]
    override fun getBlogPosts(id: Blog.ID): Repo<List<Post.ID>> = blogs[id]
    override fun getFavouritePosts(): Repo<FavouritePosts> = favouritePosts
    override fun markPostAsFavourite(id: Post.ID?) = favouritePosts.tmpChangeState(id)
}

fun createSession(client: BlogClient): Session = RepoGroup(client)

var returnScheduler: Scheduler? = null

interface BlogClient {
    fun getPostsForBlog(id: Blog.ID, offset: Int?, limit: Int?): Observable<List<Post>>
}
