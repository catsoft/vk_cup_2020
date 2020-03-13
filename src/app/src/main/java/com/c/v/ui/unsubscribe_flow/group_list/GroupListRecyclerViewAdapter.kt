package com.c.v.ui.unsubscribe_flow.group_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c.v.databinding.CellGroupBinding
import com.c.v.ui.WithIdDiffCallback
import com.c.v.ui.base.BaseAdapter
import com.c.v.utils.toVisibility
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class GroupListRecyclerViewAdapter(
    private val viewModel: GroupListViewModel,
    private val context: Context
) : BaseAdapter<GroupViewHolder, VKGroupPresentation>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellGroupBinding.inflate(layoutInflater, parent, false)
        val holder = GroupViewHolder(binding)

        RxView.clicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            viewModel.toggle(item)
            notifyItemChanged(holder.adapterPosition)
        }.addTo(compositeDisposable)

        RxView.longClicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            val action = GroupListFragmentDirections.actionNavigationGroupsListToNavigationGroupDetail(item)
            binding.root.findNavController().navigate(action)
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {

        val item = items[position]

        holder.binding.nameTextView.text = item.name

        holder.binding.selectedPart.visibility = item.isSelected.toVisibility()

        Glide.with(context).load(item.photo200).circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.mainImage)
    }

    fun updateMarketsListItems(list: List<VKGroupPresentation>) {
        val diffResult = DiffUtil.calculateDiff(WithIdDiffCallback(list, this.items))
        this.items = list.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}

