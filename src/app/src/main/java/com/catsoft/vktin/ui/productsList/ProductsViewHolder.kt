package com.catsoft.vktin.ui.productsList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_product.view.*

class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image = itemView.type_image!!
    val name = itemView.name_textView!!
    val price = itemView.info_textView!!
}