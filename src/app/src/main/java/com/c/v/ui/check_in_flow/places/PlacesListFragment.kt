package com.c.v.ui.check_in_flow.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.v.databinding.FragmentPlacesListBinding
import com.c.v.databinding.FragmentsStatesEmptyBinding
import com.c.v.databinding.FragmentsStatesErrorBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.di.Injectable
import com.c.v.ui.base.StateFragment
import com.c.v.utils.observe

class PlacesListFragment : StateFragment<FragmentPlacesListBinding>(), Injectable {

    val args: PlacesListFragmentArgs by navArgs()

    private val viewModel: PlacesListViewModel  by viewModels(factoryProducer = { viewModelFactory })

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentPlacesListBinding = FragmentPlacesListBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.fragmentsStatesEmpty

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.fragmentsStatesLoading

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.fragmentsStatesError

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        viewModel.initArgs(args.userId)

        initList(view)

        initRefresher()

        subscribe()
    }

    private fun subscribe() {
        observe(viewModel.posts) { }
        observe(viewModel.loader) { }
        observe(viewModel.userId) { }
    }

    private fun initRefresher() {
        viewBinding.swipeRefresh.setOnRefreshListener { viewModel.reload() }
    }

    private fun initList(view: View) {
        val adapter = PlacesListRecyclerViewAdapter()
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        val list = viewBinding.listRecyclerView
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.presentationPlaces.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                adapter.updateListItems(it)
                viewBinding.swipeRefresh.isRefreshing = false
            }
        })
    }
}