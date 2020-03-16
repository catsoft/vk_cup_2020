package com.c.v.ui

import androidx.recyclerview.widget.DiffUtil
import com.c.v.data.IWithIdModel

open class WithIdDiffCallback<T : IWithIdModel>(protected val newItems: List<T>, protected val oldItems: List<T>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}