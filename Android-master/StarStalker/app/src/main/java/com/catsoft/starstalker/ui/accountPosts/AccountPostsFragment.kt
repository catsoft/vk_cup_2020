package com.catsoft.starstalker.ui.accountPosts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.catsoft.starstalker.R
import com.vk.sdk.VKSdk

class AccountPostsFragment : Fragment() {

    private lateinit var accountPostsViewModel: AccountPostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountPostsViewModel =
            ViewModelProviders.of(this).get(AccountPostsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        VKSdk.login(this, "")

        return root
    }
}