package pl.kubisiak.demo.dataflow.repos

import com.tumblr.jumblr.JumblrClient
import com.tumblr.jumblr.types.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import pl.kubisiak.demo.dataflow.BaseRepo
import pl.kubisiak.demo.dataflow.RepoGroup
import pl.kubisiak.demo.dataflow.models.Blog
import pl.kubisiak.demo.dataflow.models.Post

class PostsForBlogRepo(val id: Blog.ID): BaseRepo<List<Post.ID>>(), KoinComponent {
    private val group: RepoGroup by inject()

    override fun update() {
        val client: JumblrClient by inject()
        Thread {
            val blog = client.blogInfo(id._internal)
            val posts = blog.posts()

            val retval = ArrayList<Post.ID>()
            postsLoop@ for(post in posts) {
                val id = Post.ID(post.id)
                val postModel = when (post) {
                    is PhotoPost -> Post(id, post.caption, post.photos[0].sizes[0].url)
                    is TextPost -> Post(id, post.title + " " + post.body)
                    is AnswerPost -> Post(id, post.question + " " + post.answer)
                    is ChatPost -> Post(id, post.title + " " + post.body)
                    else -> continue@postsLoop
                }
                val postRepo = group.posts[id]
                postRepo.postValue(postModel)
                retval.add(id)
            }
            postValue(retval)
        }.start()
    }
}