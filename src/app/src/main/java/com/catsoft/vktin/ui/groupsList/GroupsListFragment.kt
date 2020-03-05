package com.catsoft.vktin.ui.groupsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.catsoft.vktin.R
import com.catsoft.vktin.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_groups.*

class GroupsListFragment : StateFragment() {

    private lateinit var viewModel: GroupsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(GroupsListViewModel::class.java)

        subscribeToState(viewModel)

        viewModel.init()

        initList()

        viewModel.start()

        main_collapsing.setExpandedTitleColor(resources.getColor(R.color.transparent))
    }

    private fun initList() {
        val adapter = GroupsListRecyclerViewAdapter(viewModel, activity!!)
        val list = group_list_recycler_view
        val layoutManager = GridLayoutManager(activity!!, 3)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.groups.subscribe {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
        }.addTo(compositeDisposable)

        viewModel.subscribtion.subscribeBy {
            if(it.isNotEmpty()) {
                unsubscribe_container.visibility = View.VISIBLE
            } else {
                unsubscribe_container.visibility = View.GONE
            }
        }.addTo(compositeDisposable)

        RxView.clicks(unsubscribe_button).subscribe {
            viewModel.unsubscribe()
        }.addTo(compositeDisposable)
    }
}