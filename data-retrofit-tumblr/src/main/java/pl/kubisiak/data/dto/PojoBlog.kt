@file:Suppress("PropertyName")

package pl.kubisiak.data.dto

class PojoBlog {
    var name: String? = null
    var title: String? = null
    var description: String? = null
    var url: String? = null
    var uuid: String? = null
    var updated = 0f
}

class PostsForBlogResponse {
    var meta: PojoMeta? = null
    var response: PojoResponse? = null
}

class PojoResponse {
    var blog: PojoBlog? = null
    var posts: List<PojoPost>? = null
}

class PojoMeta {
    var status: Int? = null
    var msg: String? = null
}