package com.c.v.ui.check_in_flow.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.v.databinding.*
import com.c.v.di.Injectable
import com.c.v.di.SimpleDi
import com.c.v.services.CurrentLocaleProvider
import com.c.v.ui.base.StateFragment
import com.c.v.utils.observe
import com.c.v.utils.toVisibility

class PlacesListFragment : StateFragment<FragmentPlacesListBinding>(), Injectable {

    private val viewModel: PlacesListViewModel  by viewModels(factoryProducer = { viewModelFactory })

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentPlacesListBinding = FragmentPlacesListBinding::inflate

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

    private fun subscribe() {
        observe(viewModel.posts) { }
    }

    private fun initRefresher() {
        viewBinding.swipeRefresh.setOnRefreshListener { viewModel.loadDocs() }
    }

    private fun initList(view: View) {
        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = PlacesListRecyclerViewAdapter(locale, viewModel, requireActivity())
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        val list = viewBinding.documentListRecyclerView
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.places.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                adapter.updateDocumentsListItems(it)
                viewBinding.swipeRefresh.isRefreshing = false
            }
        })
    }
}