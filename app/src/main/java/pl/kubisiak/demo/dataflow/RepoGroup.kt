package pl.kubisiak.demo.dataflow

import pl.kubisiak.demo.dataflow.models.*
import pl.kubisiak.demo.dataflow.repos.*

class RepoGroup {
    val posts = DistinctFactory<Post.ID, PostRepo> { PostRepo(it) }
    val blogs = DistinctFactory<Blog.ID, PostsForBlogRepo> { PostsForBlogRepo(it) }
    val favouritePosts = FavouritePostsRepo()
}