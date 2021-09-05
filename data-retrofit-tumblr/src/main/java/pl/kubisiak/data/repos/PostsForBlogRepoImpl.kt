package pl.kubisiak.data.repos

import io.reactivex.Observable
import pl.kubisiak.data.dto.*

import pl.kubisiak.data.tumblr.TumblrClientKey
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post
import pl.kubisiak.dataflow.repos.PostsForBlogRepo
import javax.inject.Inject

class PostsForBlogRepoImpl @Inject constructor(
    private val http: pl.kubisiak.data.tumblr.PostsForBlog,
    private val apiKey: TumblrClientKey,
    ) : PostsForBlogRepo {

    override fun getPostsForBlog(id: Blog.ID, offset: Int?, limit: Int?): Observable<List<Post>> {
        return http
            .getPostsForBlog(id.rawValue(), apiKey.consumerKey, offset, limit)
            .map { resp ->
                resp.response!!.posts!!.mapNotNull { post ->
                    //Post(Post.ID.create(it.id!!, id), "dummy", null)

                    PostRepoImpl.mapper_post(post, Post.ID.create(post.id!!, id))

                }
            }
            .toObservable()
    }
}