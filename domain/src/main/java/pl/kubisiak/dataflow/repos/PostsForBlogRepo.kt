package pl.kubisiak.dataflow.repos

import io.reactivex.rxjava3.core.Observable
import pl.kubisiak.dataflow.models.Blog
import pl.kubisiak.dataflow.models.Post

interface PostsForBlogRepo {
    fun getPostsForBlog(id: Blog.ID, offset: Int?, limit: Int?): Observable<List<Post>>
}
