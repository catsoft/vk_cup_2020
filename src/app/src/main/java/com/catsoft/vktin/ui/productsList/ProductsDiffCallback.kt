package com.catsoft.vktin.ui.productsList

import androidx.recyclerview.widget.DiffUtil
import com.catsoft.vktin.vkApi.model.VKProduct

class ProductsDiffCallback(private val newProducts: List<VKProduct>,
                           private val oldProducts: List<VKProduct>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldProducts.size
    }

    override fun getNewListSize(): Int {
        return newProducts.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldProducts[oldItemPosition].id == newProducts[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ): Boolean {
        return oldProducts[oldItemPosition] == newProducts[newItemPosition]
    }
}