package com.catsoft.vktin.ui.documents_flow.document_list

import androidx.recyclerview.widget.DiffUtil
import com.catsoft.vktin.vkApi.model.VKDocument

class DocumentsDiffCallback(private val newDocuments: List<VKDocument>,
                            private val oldDocuments: List<VKDocument>)
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