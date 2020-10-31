package com.c.v.ui.markets_flow.product_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.c.v.data.network.vkApi.model.VKProduct
import com.c.v.databinding.CellProductBinding
import com.c.v.ui.WithIdDiffCallback
import com.c.v.ui.base.BaseAdapter
import com.c.v.utils.dpToPx
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import java.util.*

class ProductListRecyclerViewAdapter(
    locale: Locale,
    private val context: Context) : BaseAdapter<ProductViewHolder, VKProduct>() {

    private val currencyFormatter = java.text.NumberFormat.getCurrencyInstance(locale).apply {
        maximumFractionDigits = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CellProductBinding.inflate(inflater, parent, false)

        val holder = ProductViewHolder(binding)
        RxView.clicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            val navController = binding.root.findNavController()
            val action = ProductListFragmentDirections.actionNavigationProductsToNavigationProduct(item)
            navController.navigate(action)
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val item = items[position]

        val currency = Currency.getInstance(item.price.currency.name)
        currencyFormatter.currency = currency
        val price = currencyFormatter.format(item.price.amount)

        holder.binding.nameTextView.text = item.title
        holder.binding.infoTextView.text = price

        Glide.with(context).load(item.thumb_photo).transform(RoundedCorners(8F.dpToPx(context).toInt()))
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.typeImage)
    }

    fun updateMarketsListItems(list: List<VKProduct>) {
        val diffResult = DiffUtil.calculateDiff(WithIdDiffCallback(list, this.items))
        this.items = list.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}

