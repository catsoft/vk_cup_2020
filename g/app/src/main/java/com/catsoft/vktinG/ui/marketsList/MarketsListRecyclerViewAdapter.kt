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
import com.catsoft.vktinG.vkApi.model.VKGroup
import java.util.*

class MarketsListRecyclerViewAdapter(
    private val locale: Locale,
    private val viewModel: MarketsListViewModel,
    private val context: Context) : RecyclerView.Adapter<MarketsViewHolder>() {

    var documents: MutableList<VKGroup> = mutableListOf()

    override fun getItemCount(): Int = documents.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_market, parent, false)

        val holder = MarketsViewHolder(view)

        holder.itemView.setOnClickListener {
            val item = documents[holder.adapterPosition]
            val nav = view.findNavController()
            val bundle = bundleOf(Pair("title", item.name), Pair("id", item.id))
            nav.navigate(R.id.action_navigation_markets_to_navigation_products, bundle)
        }

        return holder
    }

    override fun onBindViewHolder(holder: MarketsViewHolder, position: Int) {

        val item = documents[position]

        holder.nameMarket.text = item.name
        holder.infoTextView.text = when(item.isClosed) {
            0 -> "Открытая группа"
            1 -> "Закрытая группа"
            2 -> "Частное сообщество"
            else -> ""
        }

        Glide
            .with(context)
            .load(item.photo200)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.typeImage)
    }

    fun updateMarketsListItems(list: List<VKGroup>) {
        val diffResult = DiffUtil.calculateDiff(MarketsDiffCallback(list, this.documents))
        this.documents = list.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}

