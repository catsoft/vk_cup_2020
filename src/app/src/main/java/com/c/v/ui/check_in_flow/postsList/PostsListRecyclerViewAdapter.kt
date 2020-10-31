package com.c.v.ui.check_in_flow.postsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.c.v.databinding.CellPostBinding
import com.c.v.ui.WithIdDiffCallback
import com.c.v.ui.base.BaseAdapter
import com.c.v.ui.check_in_flow.postsList.dto.PostPresentationDto


class PostsListRecyclerViewAdapter() : BaseAdapter<PostViewHolder, PostPresentationDto>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellDocumentBinding = CellPostBinding.inflate(layoutInflater, parent, false)
        val holder = PostViewHolder(cellDocumentBinding)

        return holder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val item = items[position]

        holder.binding.apply {
            titleTextView.text = item.text
            likesCountTextView.text = item.likesCount
            commentsCountTextView.text = item.commentsCount
            shareCountTextView.text = item.repostsCount
            viewCountTextView.text = item.viewsCount
        }
    }

    fun updateListItems(documents: List<PostPresentationDto>) {
        val diffResult = DiffUtil.calculateDiff(WithIdDiffCallback(documents, this.items))
        this.items = documents.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}
