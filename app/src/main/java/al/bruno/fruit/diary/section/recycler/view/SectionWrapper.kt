package al.bruno.fruit.diary.section.recycler.view

/**
 * Wrapper used to link metadata with a list item.
 *
 * @param <S> Section list item
 * @param <C> Child list item
 */
class SectionWrapper<S : Section<S, C>, C> {
    private var sectionItem = true
    private var section: S? = null
    private var child: C? = null
    private var sectionPosition = 0
    private var childPosition = 0

    /**
     * Constructor to wrap a section object of type [S].
     *
     * @param section The parent object to wrap
     */
    constructor(section: S, sectionPosition: Int) {
        sectionItem = true
        this.section = section
        this.sectionPosition = sectionPosition
        childPosition = -1
    }

    /**
     * Constructor to wrap a child object of type [C].
     *
     * @param child The child object to wrap
     */
    constructor(child: C, sectionPosition: Int, childPosition: Int) {
        this.child = child
        this.sectionPosition = sectionPosition
        sectionItem = false
        this.childPosition = childPosition
    }

    fun isSection(): Boolean {
        return sectionItem
    }

    fun getSection(): S? {
        return section
    }

    fun getChild(): C? {
        return child
    }

    fun getSectionPosition(): Int {
        return sectionPosition
    }

    fun getChildPosition(): Int {
        if (childPosition == -1) throw IllegalAccessError("This is not child")
        return childPosition
    }
}