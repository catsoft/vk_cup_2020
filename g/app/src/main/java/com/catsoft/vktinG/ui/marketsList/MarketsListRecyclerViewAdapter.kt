package com.catsoft.vktinG.ui.marketsList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.catsoft.vktinG.R
import com.catsoft.vktinG.ui.base.BaseAdapter
import com.catsoft.vktinG.vkApi.model.VKGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class MarketsListRecyclerViewAdapter(
    private val context: Context
) : BaseAdapter<MarketsViewHolder, VKGroup>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_market, parent, false)

        val holder = MarketsViewHolder(view)

        RxView.clicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            val nav = view.findNavController()
            val bundle = bundleOf(Pair("title", item.name), Pair("id", item.id))
            nav.navigate(R.id.action_navigation_markets_to_navigation_products, bundle)
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: MarketsViewHolder, position: Int) {

        val item = items[position]

        holder.nameMarket.text = item.name
        holder.infoTextView.text = when (item.isClosed) {
            0 -> "Открытая группа"
            1 -> "Закрытая группа"
            2 -> "Частное сообщество"
            else -> ""
        }

        Glide.with(context).load(item.photo200).circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.typeImage)
    }

    fun updateMarketsListItems(list: List<VKGroup>) {
        val diffResult = DiffUtil.calculateDiff(MarketsDiffCallback(list, this.items))
        this.items = list.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}

