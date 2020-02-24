package com.catsoft.vktinG.ui.productsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.catsoft.vktinG.MainActivity
import com.catsoft.vktinG.R
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.CurrentLocaleProvider
import com.catsoft.vktinG.ui.base.StateFragment
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsListFragment : StateFragment() {

    private lateinit var viewModel: ProductsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments!!
        val id = bundle.getInt("id")
        val title = bundle.getString("title")
        (activity as MainActivity).toolbar!!.title = "Товары: $title"

        viewModel = ViewModelProvider(this).get(ProductsListViewModel::class.java)

        subscribeToState(viewModel)

        viewModel.init()

        initList()

        viewModel.start(id)
    }

    private fun initList() {
        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = ProductsListRecyclerViewAdapter(locale, activity!!)
        val list = product_list_recycler_view
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