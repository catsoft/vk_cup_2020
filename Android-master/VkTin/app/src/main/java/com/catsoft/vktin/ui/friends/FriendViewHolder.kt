package com.catsoft.vktin.ui.friends

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.catsoft.vktin.R

class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var name : TextView = itemView.findViewById(R.id.friend_name)
    var avatar : ImageView = itemView.findViewById(R.id.avatar)
    var id : Int = -1
}