package com.catsoft.vktin.ui.unsubscribe_flow.group_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.catsoft.vktin.databinding.CellGroupBinding
import com.catsoft.vktin.ui.WithIdDiffCallback
import com.catsoft.vktin.ui.base.BaseAdapter
import com.catsoft.vktin.ui.unsubscribe_flow.group_detail.GroupDetailFragment
import com.catsoft.vktin.vkApi.model.VKGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class GroupListRecyclerViewAdapter(
    private val viewModel: GroupListViewModel,
    private val context: Context
) : BaseAdapter<GroupViewHolder, VKGroup>() {

    private var selectedList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellGroupBinding.inflate(layoutInflater, parent, false)
        val holder = GroupViewHolder(binding)

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
            val action = GroupListFragmentDirections.actionNavigationGroupsListToNavigationGroupDetail(item)
            binding.root.findNavController().navigate(action)
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {

        val item = items[position]

        holder.binding.nameTextView.text = item.name

        if (selectedList.contains(item.id)) {
            holder.binding.selectedPart.visibility = View.VISIBLE
        } else {
            holder.binding.selectedPart.visibility = View.GONE
        }

        Glide.with(context).load(item.photo200).circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.mainImage)
    }

    fun updateMarketsListItems(list: List<VKGroup>) {
        val diffResult = DiffUtil.calculateDiff(WithIdDiffCallback(list, this.items))
        this.items = list.toMutableList()
        selectedList.clear()
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}

