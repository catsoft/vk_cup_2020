package com.c.v.ui.check_in_flow.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.v.databinding.FragmentFriendsListBinding
import com.c.v.databinding.FragmentsStatesEmptyBinding
import com.c.v.databinding.FragmentsStatesErrorBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.di.Injectable
import com.c.v.ui.base.StateFragment
import com.c.v.utils.observe

class FriendsListFragment : StateFragment<FragmentFriendsListBinding>(), Injectable {

    private val viewModel: FriendsListViewModel  by viewModels(factoryProducer = { viewModelFactory })

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentFriendsListBinding = FragmentFriendsListBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.fragmentsStatesEmpty

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.fragmentsStatesLoading

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.fragmentsStatesError

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        initList(view)

        initRefresher()

        subscribe()
    }

    private fun subscribe() = with(viewModel) {
        observe(friends) { }

        observe(selectedFriend) {
            it?.let {
                findNavController().navigate(FriendsListFragmentDirections.actionNavigationFriendsToNavigationProfile(it))
                selectFriend(null)
            }
        }
    }

    private fun initRefresher() {
        viewBinding.swipeRefresh.setOnRefreshListener { viewModel.loadPlaces() }
    }

    private fun initList(view: View) {
        val adapter = FriendsListRecyclerViewAdapter(viewModel)
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        val list = viewBinding.documentListRecyclerView
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.presentationFriends.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                adapter.updateListItems(it)
                viewBinding.swipeRefresh.isRefreshing = false
            }
        })
    }
}