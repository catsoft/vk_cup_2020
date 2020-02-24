package com.catsoft.vktinG.ui.marketsList

import androidx.recyclerview.widget.DiffUtil
import com.catsoft.vktinG.vkApi.model.VKGroup

class MarketsDiffCallback(private val newMarkets: List<VKGroup>,
                          private val oldMarkets: List<VKGroup>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldMarkets.size
    }

    override fun getNewListSize(): Int {
        return newMarkets.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMarkets[oldItemPosition].id == newMarkets[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ): Boolean {
        return oldMarkets[oldItemPosition] == newMarkets[newItemPosition]
    }
}