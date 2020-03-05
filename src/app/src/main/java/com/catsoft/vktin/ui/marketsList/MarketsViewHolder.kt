package com.catsoft.vktin.ui.marketsList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_market.view.*

class MarketsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val typeImage = itemView.type_image!!
    val nameMarket = itemView.name_textView!!
    val infoTextView = itemView.info_textView!!
}