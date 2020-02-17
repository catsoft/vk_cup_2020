package com.catsoft.vktin.ui.friends

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.catsoft.vktin.R
import com.vk.sdk.api.model.VKApiUserFull

class FriendsRecyclerViewAdapter(var activity: Activity, var navController : NavController) : RecyclerView.Adapter<FriendViewHolder>() {

    var friends : Array<VKApiUserFull> = arrayOf()

    override fun getItemCount(): Int = friends.size

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        var item = friends[position]

        val fullName = "${item.first_name} ${item.last_name}"
        holder.name.text = fullName
        holder.id = item.id

        Glide.with(holder.avatar)
            .load(item.photo_200)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .onlyRetrieveFromCache(true)
            .transform(CircleCrop())
            .placeholder(R.drawable.avatar)
            .into(holder.avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_friend, parent, false)

        var holder = FriendViewHolder(view)

        return holder
    }
}