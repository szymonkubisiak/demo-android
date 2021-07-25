package pl.kubisiak.data.tumblr

import io.reactivex.Single
import pl.kubisiak.data.dto.PostsForBlogResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PostsForBlog {

    //api.tumblr.com/v2/blog/{blog-identifier}/posts[/type]?api_key={key}&[optional-params=]
    @GET("blog/{blog-identifier}/posts")
    fun getPostsForBlog(
        @Path("blog-identifier") blogId: String,
        @Query("api_key") apiKey: String,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?,
    ) : Single<PostsForBlogResponse>
}