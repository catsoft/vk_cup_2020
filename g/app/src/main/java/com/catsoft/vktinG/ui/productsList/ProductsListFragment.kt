package com.catsoft.vktinG.ui.productsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.catsoft.vktinG.MainActivity
import com.catsoft.vktinG.R
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.CurrentLocaleProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsListFragment : Fragment() {

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

        viewModel.setId(id)

        viewModel.groups.error.observe(this as LifecycleOwner, Observer {
            if(it != null) {
                Toast.makeText(activity, "Произошла ошибка", Toast.LENGTH_LONG).show()
            }
        })

        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = ProductsListRecyclerViewAdapter(locale, viewModel, activity!!)
        val list = product_list_recycler_view
        val layoutManager = GridLayoutManager(view.context, 2)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.groups.data.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
        })

        viewModel.loadProducts()
    }
}