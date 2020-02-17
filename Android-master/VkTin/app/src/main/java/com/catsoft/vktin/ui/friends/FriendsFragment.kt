package com.catsoft.vktin.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.catsoft.vktin.R
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView
import kotlinx.android.synthetic.main.fragment_friends.view.*

class FriendsFragment : Fragment() {

    private lateinit var friendsViewModel: FriendsViewModel

    private lateinit var recyclerView: CardStackView

    private lateinit var adapter: FriendsRecyclerViewAdapter

    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        friendsViewModel = ViewModelProviders.of(this).get(FriendsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_friends, container, false)

        var navController = findNavController()

        recyclerView = root.findViewById(R.id.friends_recycler_view)
        recyclerView.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
        cardStackLayoutManager = CardStackLayoutManager(this.activity)
        recyclerView.layoutManager = cardStackLayoutManager
        adapter = FriendsRecyclerViewAdapter(activity!!, navController)
        recyclerView.adapter = adapter

        root.like_button.setOnClickListener {
            recyclerView.swipe()
        }

        root.dislike_button.setOnClickListener {
            recyclerView.swipe()
        }

        root.back_button.setOnClickListener {
            recyclerView.rewind()
        }

        friendsViewModel.friends.observe(this, Observer {
            adapter.friends = it
            adapter.notifyDataSetChanged()
        })

        friendsViewModel.loadFriends()

        return root
    }
}

