package com.c.v.ui.check_in_flow.postsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.c.v.R
import com.c.v.databinding.FragmentPostsListBinding
import com.c.v.ui.auth.AuthViewModel
import com.c.v.ui.base.StateFragment

class PostsListFragment : StateFragment<FragmentPostsListBinding>() {

    val args: PostsListFragmentArgs by navArgs()

    private val viewModel: PostsListViewModel by viewModels()

    private val authViewModel : AuthViewModel by activityViewModels()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentPostsListBinding = FragmentPostsListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        authViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (!it) {
                findNavController().navigate(R.id.navigation_auth)
            }
        })
    }
}

