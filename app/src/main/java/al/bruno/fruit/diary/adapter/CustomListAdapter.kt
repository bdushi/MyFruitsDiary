package al.bruno.fruit.diary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class CustomListAdapter<T, VM: ViewDataBinding>(
    private val r: Int,
    private val bindingData: BindingData<T, VM>,
    diffUtil: DiffUtil.ItemCallback<T>) : ListAdapter<T, CustomViewHolder<T, VM>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<T, VM> {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(r, parent, false), bindingData)
    }

    override fun onBindViewHolder(holder: CustomViewHolder<T, VM>, position: Int) {
        getItem(position)?.let{ holder.bind(it) }
    }


    public override fun getItem(position: Int): T {
        return super.getItem(position)
    }
    /*override fun getItemCount(): Int {
        return t.size
    }*/

    /*object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem }*/
}