package pl.kubisiak.dataflow

import pl.kubisiak.dataflow.models.*
import pl.kubisiak.dataflow.repos.*

class RepoGroup {
    val posts = DistinctFactory<Post.ID, PostRepo> { PostRepo(it) }
    val blogs = DistinctFactory<Blog.ID, PostsForBlogRepo> { PostsForBlogRepo(it) }
    val favouritePosts = FavouritePostsRepo()
}