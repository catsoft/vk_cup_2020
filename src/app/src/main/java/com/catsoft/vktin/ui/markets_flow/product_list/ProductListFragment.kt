package com.catsoft.vktin.ui.markets_flow.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.catsoft.vktin.MainActivity
import com.catsoft.vktin.databinding.FragmentProductsBinding
import com.catsoft.vktin.databinding.FragmentsStatesEmptyBinding
import com.catsoft.vktin.databinding.FragmentsStatesErrorBinding
import com.catsoft.vktin.databinding.FragmentsStatesLoadingBinding
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.CurrentLocaleProvider
import com.catsoft.vktin.ui.base.StateFragment

class ProductListFragment : StateFragment<FragmentProductsBinding>() {

    private val viewModel: ProductsListViewModel by viewModels()

    private val args: ProductListFragmentArgs by navArgs()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductsBinding = FragmentProductsBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.statesEmpty

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.statesLoading

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.statesError

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).viewBinding.toolbar.title = "Товары: ${args.selectedMarket.name}"

        viewModel.start(args.selectedMarket.id)

        subscribeToState(viewModel)

        initList()
    }

    private fun initList() {
        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = ProductListRecyclerViewAdapter(locale, activity!!)
        val list = viewBinding.productListRecyclerView
        val layoutManager = GridLayoutManager(activity!!, 2)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
            viewBinding.swipeRefresh.isRefreshing = false
        })

        viewBinding.swipeRefresh.setOnRefreshListener(viewModel::reload)
    }

    override fun onDestroyView() {
        viewBinding.swipeRefresh.setOnRefreshListener { }
        super.onDestroyView()
    }
}