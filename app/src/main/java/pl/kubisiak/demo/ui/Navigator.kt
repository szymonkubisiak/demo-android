package pl.kubisiak.demo.ui

import pl.kubisiak.demo.dataflow.models.*

interface Navigator {

    /**
     * @return true if Fragment was popped
     */
    fun goBack(): Boolean

    fun goToFirstScreen()
    fun goToListTest()
    fun goToPostsList(id: Blog.ID)
    fun goToPostDetail(id: Post.ID)

    fun debugToast(text: CharSequence, long: Boolean = true)
}