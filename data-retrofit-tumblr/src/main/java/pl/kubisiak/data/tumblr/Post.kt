package pl.kubisiak.data.tumblr

import io.reactivex.Single
import pl.kubisiak.data.dto.PojoPost
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Post {
    //TODO: requires OAuth
    //api.tumblr.com/v2/blog/{blog-identifier}/posts/{post-id}
    @GET("blog/{blog-identifier}/posts/{post-id}?post_format=legacy&reblog_info=true")
    fun getPost(
        @Path("blog-identifier") blogId: String,
        @Path("post-id") postId: Long,
        @Query("api_key") apiKey: String,
    ): Single<PojoPost>
}