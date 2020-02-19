package al.bruno.fruit.diary.listener

import android.view.View

interface ViewOnClickListener<T> {
    fun onClick(v: View, t: T)

}