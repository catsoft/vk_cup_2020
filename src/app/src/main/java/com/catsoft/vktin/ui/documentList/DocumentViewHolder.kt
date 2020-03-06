package com.catsoft.vktin.ui.documentList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_document.view.*

class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val typeContainer = itemView.type_container!!
    val typeImage = itemView.type_image!!
    val nameEditText = itemView.name_textView!!
    val infoTextView = itemView.info_textView!!
    val dotsImageView = itemView.dots!!
    val tagsTextView = itemView.tags_text_view!!
    val tagsContainer = itemView.tags_container!!
    val loadedImage = itemView.loadedImageView!!
}