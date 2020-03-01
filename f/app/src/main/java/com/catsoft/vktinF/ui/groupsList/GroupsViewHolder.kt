package com.catsoft.vktinF.ui.groupsList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_group.view.*

class GroupsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mainImage = itemView.main_image!!
    val nameMarket = itemView.name_textView!!
    val selectedPart = itemView.selected_part!!
}