package com.amdtia.tracking.app.listener

interface OnItemSwipeSelectListener<T> {
    fun onItemSwipedLeft(position:Int, t: T)
    fun onItemSwipedRight(position:Int, t: T)
}