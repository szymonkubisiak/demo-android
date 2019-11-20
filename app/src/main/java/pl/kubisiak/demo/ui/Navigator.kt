package pl.kubisiak.demo.ui

interface Navigator {

    /**
     * @return true if Fragment was popped
     */
    fun goBack(): Boolean

    fun goToFirstScreen()
    fun goToListTest()
    fun goToPostsList()

    fun debugToast(text: CharSequence, long: Boolean = true)
}