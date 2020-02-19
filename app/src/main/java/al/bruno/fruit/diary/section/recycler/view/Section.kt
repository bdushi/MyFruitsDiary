package al.bruno.fruit.diary.section.recycler.view

/**
 * @param <C> Child list item
 */

interface Section<S, C> {
    fun section(): S
    fun items(): List<C>
}