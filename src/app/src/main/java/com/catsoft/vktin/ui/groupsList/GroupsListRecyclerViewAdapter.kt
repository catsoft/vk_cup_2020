package com.catsoft.vktin.ui.groupsList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.catsoft.vktin.R
import com.catsoft.vktin.ui.base.BaseAdapter
import com.catsoft.vktin.ui.groupsDetail.GroupDetailFragment
import com.catsoft.vktin.vkApi.model.VKGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class GroupsListRecyclerViewAdapter(
    private val viewModel: GroupsListViewModel,
    private val context: Context
) : BaseAdapter<GroupsViewHolder, VKGroup>() {

    private var selectedList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_group, parent, false)

        val holder = GroupsViewHolder(view)

        RxView.clicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            viewModel.toggle(item.id)

            if (selectedList.contains(item.id)) {
                selectedList.remove(item.id)
            } else {
                selectedList.add(item.id)
            }
            notifyItemChanged(holder.adapterPosition)

        }.addTo(compositeDisposable)

        RxView.longClicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            val dialogFragment = GroupDetailFragment(item)
            dialogFragment.show((context as AppCompatActivity).supportFragmentManager, "signature")
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {

        val item = items[position]

        holder.nameMarket.text = item.name

        if (selectedList.contains(item.id)) {
            holder.selectedPart.visibility = View.VISIBLE
        } else {
            holder.selectedPart.visibility = View.GONE
        }

        Glide.with(context).load(item.photo200).circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mainImage)
    }

    fun updateMarketsListItems(list: List<VKGroup>) {
        val diffResult = DiffUtil.calculateDiff(GroupsDiffCallback(list, this.items))
        this.items = list.toMutableList()
        selectedList.clear()
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}

