package al.bruno.fruit.diary.adapter

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding

interface BindingChildData<S, C, VM : ViewDataBinding> {
    fun bindData(t: S?, c: C?, @NonNull vm: VM)
}