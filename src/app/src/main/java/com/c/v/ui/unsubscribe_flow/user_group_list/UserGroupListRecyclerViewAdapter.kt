package com.c.v.ui.unsubscribe_flow.user_group_list

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c.v.R
import com.c.v.databinding.CellGroupBinding
import com.c.v.ui.base.BaseAdapter
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class UserGroupListRecyclerViewAdapter(
    private val viewModel: UserGroupListViewModel,
    private val context: Context
) : BaseAdapter<UserGroupViewHolder, VKUserGroupItemPresentation>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGroupViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellGroupBinding.inflate(layoutInflater, parent, false)
        val holder = UserGroupViewHolder(binding)

        RxView.clicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            viewModel.toggle(item)
            notifyItemChanged(holder.adapterPosition)
        }.addTo(compositeDisposable)

        RxView.longClicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            val action = UserGroupListFragmentDirections.actionNavigationGroupsListToNavigationGroupDetail(item.id)
            binding.root.findNavController().navigate(action)
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: UserGroupViewHolder, position: Int) {

        val item = items[position]

        holder.binding.nameTextView.text = item.name

        val isSelected = context.resources.getDrawable(R.drawable.ic_selected_back, context.theme)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.binding.mainImage.foreground = if (item.isSelected) isSelected else null
        }

        Glide.with(context).load(item.photo200).circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.mainImage)
    }

    fun updateMarketsListItems(list: List<VKUserGroupItemPresentation>) {
        val diffResult = DiffUtil.calculateDiff(UserGroupDiffCallback(list, this.items))
        this.items = list.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}

