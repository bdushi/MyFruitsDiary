package al.bruno.fruit.diary.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ChildViewHolder<S, C, VM : ViewDataBinding>(itemView: View, private val bindingChildData: BindingChildData<S, C, VM>) : RecyclerView.ViewHolder(itemView) {
    private val binding: VM? = DataBindingUtil.bind(itemView)
    fun bind(t: S?, c: C?) {
        binding?.let{
            bindingChildData.bindData(t, c, it)
            it.executePendingBindings()
        }
    }
}