package com.catsoft.vktin.ui.cities

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_city.view.*

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.title_textView!!
    val checkImage = itemView.check_icon!!
}