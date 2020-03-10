package com.catsoft.vktin.ui.markets_flow.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.catsoft.vktin.MainActivity
import com.catsoft.vktin.databinding.FragmentProductsBinding
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.CurrentLocaleProvider
import com.catsoft.vktin.ui.base.StateFragment
import io.reactivex.rxkotlin.addTo

class ProductListFragment : StateFragment<FragmentProductsBinding>() {

    private lateinit var viewModel: ProductsListViewModel

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductsBinding = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments!!
        val id = bundle.getInt("id")
        val title = bundle.getString("title")
        (requireActivity() as MainActivity).viewBinding.toolbar.title = "Товары: $title"

        viewModel = ViewModelProvider(this).get(ProductsListViewModel::class.java)

        subscribeToState(viewModel)

        initList()
        viewModel.start(id)
    }

    private fun initList() {
        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = ProductListRecyclerViewAdapter(locale, activity!!)
        val list = viewBinding.productListRecyclerView
        val layoutManager = GridLayoutManager(activity!!, 2)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.products.subscribe {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
        }.addTo(compositeDisposable)
    }
}