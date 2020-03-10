package com.catsoft.vktin.ui.markets_flow.market_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.catsoft.vktin.R
import com.catsoft.vktin.databinding.CellMarketBinding
import com.catsoft.vktin.ui.WithIdDiffCallback
import com.catsoft.vktin.ui.base.BaseAdapter
import com.catsoft.vktin.vkApi.model.VKGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class MarketListRecyclerViewAdapter(
    private val context: Context
) : BaseAdapter<MarketViewHolder, VKGroup>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellMarketBinding.inflate(layoutInflater, parent, false)
        val holder = MarketViewHolder(binding)

        RxView.clicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            val nav = binding.root.findNavController()
            val bundle = bundleOf(Pair("title", item.name), Pair("id", item.id))
            nav.navigate(R.id.action_navigation_markets_to_navigation_products, bundle)
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {

        val item = items[position]

        holder.binding.nameTextView.text = item.name
        holder.binding.infoTextView.text = when (item.isClosed) {
            0 -> "Открытая группа"
            1 -> "Закрытая группа"
            2 -> "Частное сообщество"
            else -> ""
        }

        Glide.with(context).load(item.photo200).circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.typeImage)
    }

    fun updateMarketsListItems(list: List<VKGroup>) {
        val diffResult = DiffUtil.calculateDiff(WithIdDiffCallback(list, this.items))
        this.items = list.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}

