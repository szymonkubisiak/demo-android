package pl.kubisiak.data.tumblr

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Post {
    //api.tumblr.com/v2/blog/{blog-identifier}/posts/{post-id}
    @GET("blog/{blog-identifier}/posts/{post-id}?post_format=legacy")
    fun getPost(@Path("blog-identifier") blogId: String, @Path("post-id") postId:Long): Single<Any>
}