package ru.netology.myrecipes.ItemTouchHelper

interface TouchHelperAdapter {
    fun onMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}