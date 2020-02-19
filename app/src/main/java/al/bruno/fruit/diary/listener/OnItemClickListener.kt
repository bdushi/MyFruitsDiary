package al.bruno.fruit.diary.listener

interface OnItemClickListener<T> {
    fun onItemClick(t: T)
    fun onLongItemClick(t: T): Boolean
}