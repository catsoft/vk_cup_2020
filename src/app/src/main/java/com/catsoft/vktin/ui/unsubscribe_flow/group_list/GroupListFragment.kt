package com.catsoft.vktin.ui.unsubscribe_flow.group_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.catsoft.vktin.R
import com.catsoft.vktin.databinding.FragmentGroupsBinding
import com.catsoft.vktin.databinding.FragmentsStatesEmptyBinding
import com.catsoft.vktin.databinding.FragmentsStatesErrorBinding
import com.catsoft.vktin.databinding.FragmentsStatesLoadingBinding
import com.catsoft.vktin.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class GroupListFragment : StateFragment<FragmentGroupsBinding>() {

    private val viewModel: GroupListViewModel by viewModels()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentGroupsBinding = FragmentGroupsBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.statesEmpty

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.statesError

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.statesLoading

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        initList()

        viewBinding.mainCollapsing.setExpandedTitleColor(resources.getColor(R.color.transparent))
    }

    private fun initList() {
        val adapter = GroupListRecyclerViewAdapter(viewModel, activity!!)
        val list = viewBinding.groupListRecyclerView
        val layoutManager = GridLayoutManager(activity!!, 3)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.groups.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
            viewBinding.swipeRefresh.isRefreshing = false
        })

        viewModel.subscription.subscribeBy {
            if (it.isNotEmpty()) {
                viewBinding.unsubscribeContainer.visibility = View.VISIBLE
            } else {
                viewBinding.unsubscribeContainer.visibility = View.GONE
            }
        }.addTo(compositeDisposable)

        viewBinding.swipeRefresh.setOnRefreshListener {
            viewModel.load()
        }

        RxView.clicks(viewBinding.unsubscribeButton).subscribe {
            viewModel.unsubscribe()
        }.addTo(compositeDisposable)
    }

    override fun onDestroyView() {
        viewBinding.swipeRefresh.setOnRefreshListener { }

        super.onDestroyView()
    }
}