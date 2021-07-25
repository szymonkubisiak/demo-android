@file:Suppress("PropertyName")

package pl.kubisiak.data.dto

/**
 * data transfer models according to https://www.tumblr.com/docs/en/api/v2
 * This is the layer closest to API specs, so names should adhere to it as closely as possible
 */
sealed class PojoPost {
    //var type: String? = null //type is stripped by RuntimeTypeAdapterFactory.maintainType
    var blog_name: String? = null
    var blog: PojoBlog? = null
    var id: Long? = null
    var id_string: String? = null
    var post_url: String? = null
    var slug: String? = null
    var date: String? = null
    var timestamp: Long? = null
    var state: String? = null
    var format: String? = null
    var reblog_key: String? = null
    var tags = ArrayList<Any>()
    var short_url: String? = null
    var summary: String? = null
    var should_open_in_legacy: Boolean? = null
    var recommended_source: String? = null
    var recommended_color: String? = null
    var note_count: Long? = null
    var reblog: Reblog? = null

    //    var trail = ArrayList<Any>()
    var can_like: Boolean? = null
    var can_reblog: Boolean? = null
    var can_send_in_message: Boolean? = null
    var can_reply: Boolean? = null
    var display_avatar: Boolean? = null

    //from api docs, not seen in the wild
    var source_url: String? = null
    var source_title: String? = null

    //as seen in jumblr
    var reblogged_from_name: String? = null

    val rebloggedFromName: String?
        get() {
            return reblogged_from_name
        }

    //implementations:

    class Unknown : PojoPost()

    class Text : PojoPost() {
        var title: String? = null
        var body: String? = null
    }

    class Chat : PojoPost() {
        var title: String? = null
        var body: String? = null
    }

    class Answer : PojoPost() {
        var question: String? = null
        var answer: String? = null
    }

    class Quote : PojoPost() {
        var text: String? = null
        var source: String? = null
    }

    open class Photo : PojoPost() {
        var caption: String? = null
        var width: Int? = null
        var height: Int? = null
        var photos: List<PojoPhoto>? = null
    }

    class Link : Photo() {
        var title: String? = null
        var url: String? = null
        var link_author: String? = null
        var excerpt: String? = null
        var publisher: String? = null
        var description: String? = null
    }

    //TODO: implement
    class Audio : PojoPost()
    class Video : PojoPost()
    class Postcard : PojoPost()
}

class Reblog {
    var comment: String? = null
    var tree_html: String? = null
}

class PojoPhotosize {
    var width: Int? = null
    var height: Int? = null
    var url: String? = null
}

class PojoPhoto {
    var caption: String? = null
    var alt_sizes: List<PojoPhotosize>? = null

    val sizes: List<PojoPhotosize>?
        get() {
            return alt_sizes
        }
}


