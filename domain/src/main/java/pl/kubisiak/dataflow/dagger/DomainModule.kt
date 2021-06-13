package pl.kubisiak.dataflow.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.kubisiak.dataflow.*
import pl.kubisiak.dataflow.models.*
import pl.kubisiak.dataflow.sources.*

@Module(includes = [DomainModuleInternal::class, DomainModuleManual::class])
interface DomainModule {
}

@Module
internal interface DomainModuleInternal {
    @Binds
    fun bindPostDepot(impl: PostsDepot): Depot<Post.ID, PostSource>

    @Binds
    fun bindPostsForBlogPaginatedDepot(impl: PostsForBlogPaginatedDepot): Depot<Blog.ID, PostsForBlogPaginatedSource>

    @Binds
    fun bindPostsForBlogDepot(impl: PostsForBlogDepot): Depot<Blog.ID, PostsForBlogSource>
}

@Module
internal class DomainModuleManual {
    @Provides
    fun bindPostDepot(impl: PostsDepot): Depot<Post.ID, Source<Post>> {
        return impl
    }

    @Provides
    fun bindPostsForBlogPaginatedDepot(impl: PostsForBlogPaginatedDepot): Depot<Blog.ID, Source<Pager<List<Post.ID>>>> {
        return impl
    }
}
