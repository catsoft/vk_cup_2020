package com.catsoft.vktinG.ui.productsList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catsoft.vktinG.R
import com.catsoft.vktinG.utils.DimensionUtil
import com.catsoft.vktinG.vkApi.model.VKProduct
import java.util.*


class ProductsListRecyclerViewAdapter(
    private val locale: Locale,
    private val viewModel: ProductsListViewModel,
    private val context: Context) : RecyclerView.Adapter<ProductsViewHolder>() {

    var documents: MutableList<VKProduct> = mutableListOf()

    override fun getItemCount(): Int = documents.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_product, parent, false)

        val holder = ProductsViewHolder(view)

        holder.itemView.setOnClickListener {
            val item = documents[holder.adapterPosition]
            val nav = view.findNavController()
            val bundle = bundleOf(Pair("title", item.title), Pair("id", item.id))
            nav.navigate(R.id.action_navigation_products_to_navigation_product, bundle)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {

        val item = documents[position]

        val currency = Currency.getInstance(item.price.currency.name)
        val currencyFormatter = java.text.NumberFormat.getCurrencyInstance(locale)
        currencyFormatter.maximumFractionDigits = 0
        currencyFormatter.currency = currency
        val price = currencyFormatter.format(item.price.amount)

        holder.name.text = item.title
        holder.price.text = price

        Glide
            .with(context)
            .load(item.thumb_photo)
            .transform(RoundedCorners(DimensionUtil.convertDpToPixel(8F, context).toInt()))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)
    }

    fun updateMarketsListItems(list: List<VKProduct>) {
        val diffResult = DiffUtil.calculateDiff(ProductsDiffCallback(list, this.documents))
        this.documents = list.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}

