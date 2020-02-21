package com.catsoft.vktinG.ui.marketsList

import androidx.recyclerview.widget.DiffUtil
import com.catsoft.vktinG.vkApi.model.VKGroup

class MarketsDiffCallback(private val newDocuments: List<VKGroup>,
                          private val oldDocuments: List<VKGroup>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldDocuments.size
    }

    override fun getNewListSize(): Int {
        return newDocuments.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDocuments[oldItemPosition].id == newDocuments[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ): Boolean {
        return oldDocuments[oldItemPosition] == newDocuments[newItemPosition]
    }
}