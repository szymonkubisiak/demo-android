package pl.kubisiak.dataflow

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

interface BlogClient {
    fun getPostsForBlogSync(id: Blog.ID): List<Post>
}
