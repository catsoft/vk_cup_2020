package com.c.v.ui.check_in_flow.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.c.v.R
import com.c.v.databinding.CellFriendBinding
import com.c.v.ui.WithIdDiffCallback
import com.c.v.ui.base.BaseAdapter
import com.c.v.ui.check_in_flow.friends.dto.FriendsPresentationDto
import com.c.v.utils.dpToPx
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class FriendsListRecyclerViewAdapter(val viewModel: FriendsListViewModel) : BaseAdapter<FriendsViewHolder, FriendsPresentationDto>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellDocumentBinding = CellFriendBinding.inflate(layoutInflater, parent, false)
        val holder = FriendsViewHolder(cellDocumentBinding)

        holder.binding.apply {
            RxView.clicks(root).subscribe {
                if (holder.adapterPosition in items.indices) {
                    val item = items[holder.adapterPosition]
                    viewModel.selectFriend(item.id)
                }
            }.addTo(compositeDisposable)
        }

        return holder
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {

        val item = items[position]

        holder.binding.apply {
            titleTextView.text = item.title
            subtitleTextView.text = item.subtitle

            if(item.icon.isEmpty()) {
                icon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_person))
            } else {
                Glide.with(holder.itemView.context)
                    .load(item.icon)
                    .transform(RoundedCorners(8F.dpToPx(holder.itemView.context).toInt()))
                    .into(icon)
            }
        }
    }

    fun updateListItems(documents: List<FriendsPresentationDto>) {
        val diffResult = DiffUtil.calculateDiff(WithIdDiffCallback(documents, this.items))
        this.items = documents.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}
