package al.bruno.fruit.diary.section.recycler.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerViewSectionAdapter<
        S : Section<S, C>,
        C,
        SVH : RecyclerView.ViewHolder,
        CVH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * Default ViewType for section rows
     */
    private val SECTION_VIEW_TYPE = 1

    /**
     * Default ViewType for child rows
     */
    private val CHILD_VIEW_TYPE = 2

    /**
     * A [List] of all sections and their children, in order.
     * Changes to this list should be made through the add/remove methods
     * available in [SectionRecyclerViewAdapter].
     */
    private var flatItemList: List<SectionWrapper<S, C>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isSectionViewType(viewType)) {
            onCreateSectionViewHolder(parent, viewType)
        } else {
            onCreateChildViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        check(position <= flatItemList.size) {
            ("Trying to bind item out of bounds, size " + flatItemList.size + " flatPosition " + position + ". Was the data changed without a call to notify...()?")
        }
        flatItemList[position].let {
            if (it.isSection()) {
                onBindSectionViewHolder(holder as SVH, it.getSectionPosition(), it.getSection())
            } else {
                onBindChildViewHolder(holder as CVH, it.getSection(), it.getChildPosition(), it.getChild())
            }
        }
    }

    override fun getItemCount(): Int {
        return flatItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (flatItemList[position].isSection()) SECTION_VIEW_TYPE else CHILD_VIEW_TYPE
    }

    fun submitList(list: List<S>?) {
        this.flatItemList = generateFlatItemList(list)
        notifyDataSetChanged()
    }

    private fun isSectionViewType(viewType: Int): Boolean {
        return viewType == SECTION_VIEW_TYPE
    }

    /**
     * Generates a full list of all sections and their children, in order.
     *
     * @param sectionItemList A list of the sections from
     * the [SectionRecyclerViewAdapter]
     * @return A list of all sections and their children
     */
    private fun generateFlatItemList(sectionItemList: List<S>?): List<SectionWrapper<S, C>> {
        val flatItemList: ArrayList<SectionWrapper<S, C>> = ArrayList()
        sectionItemList?.let {
            for (i in it.indices) {
                val section = sectionItemList[i]
                generateSectionWrapper(flatItemList, section, i)
            }
        }
        return flatItemList
    }

    private fun generateSectionWrapper(flatItemList: ArrayList<SectionWrapper<S, C>>, section: S, sectionPosition: Int) {
        val sectionWrapper = SectionWrapper(section, sectionPosition)
        flatItemList.add(sectionWrapper)
        val childList: List<C> = section.items()
        for (i in childList.indices) {
            val childWrapper = SectionWrapper<S, C>(childList[i], sectionPosition, i)
            flatItemList.add(childWrapper)
        }
    }

    abstract fun onCreateSectionViewHolder(sectionViewGroup: ViewGroup?, viewType: Int): SVH
    abstract fun onCreateChildViewHolder(childViewGroup: ViewGroup?, viewType: Int): CVH

    abstract fun onBindSectionViewHolder(sectionViewHolder: SVH, sectionPosition: Int, section: S?)
    abstract fun onBindChildViewHolder(childViewHolder: CVH, section: S?, childPosition: Int, child: C?)

}