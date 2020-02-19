package al.bruno.fruit.diary.adapter

import al.bruno.fruit.diary.section.recycler.view.RecyclerViewSectionAdapter
import al.bruno.fruit.diary.section.recycler.view.Section
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

class SectionAdapter<
        S : Section<S, C>,
        C,
        SVH : ViewDataBinding,
        CVH : ViewDataBinding>(
    private val s: Int,
    private val c: Int,
    private val bindingData: BindingData<S, SVH>,
    private val childData:  BindingChildData<S, C, CVH>) :
    RecyclerViewSectionAdapter<
            S,
            C,
            CustomViewHolder<S, SVH>,
            ChildViewHolder<S, C, CVH>>() {

    override fun onCreateSectionViewHolder(sectionViewGroup: ViewGroup?, viewType: Int): CustomViewHolder<S, SVH> {
        return CustomViewHolder(LayoutInflater.from(sectionViewGroup?.context).inflate(s, sectionViewGroup, false), bindingData)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup?, viewType: Int): ChildViewHolder<S, C, CVH> {
        return ChildViewHolder(LayoutInflater.from(childViewGroup?.context).inflate(c, childViewGroup, false), childData)
    }

    override fun onBindSectionViewHolder(sectionViewHolder: CustomViewHolder<S, SVH>, sectionPosition: Int, section: S?) {
        sectionViewHolder.bind(section)
    }

    override fun onBindChildViewHolder(childViewHolder: ChildViewHolder<S, C, CVH>, section: S?, childPosition: Int, child: C?) {
        childViewHolder.bind(section, child)
    }
}