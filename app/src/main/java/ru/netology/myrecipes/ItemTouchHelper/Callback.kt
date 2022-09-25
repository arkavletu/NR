package ru.netology.myrecipes.ItemTouchHelper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myrecipes.RecipesAdapter

class Callback(adapter: TouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    private val mAdapter: TouchHelperAdapter = adapter
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter.onMove(viewHolder.absoluteAdapterPosition, target.absoluteAdapterPosition)
        return true
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mAdapter.onItemDismiss(viewHolder.absoluteAdapterPosition)
    }

//    init {
//        mAdapter = adapter
//    }
}