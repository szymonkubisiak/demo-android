package pl.kubisiak.demo.dataflow.repos

import pl.kubisiak.demo.dataflow.BaseRepo
import pl.kubisiak.demo.dataflow.models.Post

class PostRepo(val id: Post.ID): BaseRepo<Post>() {
    override fun update() {
        //TODO tumblr client update post details
    }
}