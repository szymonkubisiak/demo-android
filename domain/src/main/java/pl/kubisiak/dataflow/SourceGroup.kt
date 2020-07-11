package pl.kubisiak.dataflow

import io.reactivex.Observable
import org.koin.dsl.module
import pl.kubisiak.dataflow.models.*
import pl.kubisiak.dataflow.sources.*

internal class PostSourceDF : DistinctFactory<Post.ID, PostSource>({ PostSource(it) })
internal class PostsForBlogSourceDF : DistinctFactory<Blog.ID, PostsForBlogSource>({ PostsForBlogSource(it) })

interface BlogClient {
    fun getPostsForBlog(id: Blog.ID, offset: Int?, limit: Int?): Observable<List<Post>>
    fun getPost(id: Post.ID): Observable<Post>
}

val sourcesModule = module {
    single { PostSourceDF() }
    single { PostsForBlogSourceDF() }

    single { FavouritePostsSource() }

    factory { (id: Post.ID) -> get<PostSourceDF>()[id] }
    factory { (id: Blog.ID) -> get<PostsForBlogSourceDF>()[id] }
}
