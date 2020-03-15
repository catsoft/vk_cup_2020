package com.c.v.ui.unsubscribe_flow.group_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.c.v.R
import com.c.v.databinding.FragmentGroupsBinding
import com.c.v.databinding.FragmentsStatesEmptyBinding
import com.c.v.databinding.FragmentsStatesErrorBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.di.Injectable
import com.c.v.ui.base.StateFragment
import com.c.v.utils.observe
import com.c.v.utils.onRefreshObserver
import com.c.v.utils.toVisibility
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class GroupListFragment : StateFragment<FragmentGroupsBinding>(), Injectable {

    private val viewModel: GroupListViewModel by viewModels(factoryProducer = {viewModelFactory})

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentGroupsBinding = FragmentGroupsBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.statesEmpty

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.statesError

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.statesLoading

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        initList()

        initUnsubscribeButton()

        initSwipeRefresher()
    }

    private fun initList() {
        val adapter = GroupListRecyclerViewAdapter(viewModel, requireContext())
        viewBinding.groupListRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            this.adapter = adapter
        }

        observe(viewModel.list) {
            adapter.updateMarketsListItems(it)

            viewBinding.swipeRefresh.isRefreshing = false
        }

        observe(viewModel.isAnySelected) { viewBinding.unsubscribeContainer.visibility = it.toVisibility() }
    }

    private fun initUnsubscribeButton() {
        RxView.clicks(viewBinding.unsubscribeButton).subscribe { viewModel.unsubscribe() }.addTo(compositeDisposable)
    }

    private fun initSwipeRefresher() {
        viewBinding.swipeRefresh.onRefreshObserver().subscribe { viewModel.load() }.addTo(compositeDisposable)
    }

    override fun onDestroyView() {
        viewBinding.swipeRefresh.setOnRefreshListener { }

        super.onDestroyView()
    }
}