package com.catsoft.vktinG.ui.marketsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktinG.R
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.CurrentLocaleProvider
import kotlinx.android.synthetic.main.fragment_markets.*

class MarketsListFragment : Fragment() {

    private lateinit var viewModel: MarketsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_markets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MarketsListViewModel::class.java)

        viewModel.groups.error.observe(this as LifecycleOwner, Observer {
            if(it != null) {
                Toast.makeText(activity, "Произошла ошибка", Toast.LENGTH_LONG).show()
            }
        })

        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = MarketsListRecyclerViewAdapter(locale, viewModel, activity!!)
        val list = market_list_recycler_view
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.groups.data.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
        })

        viewModel.loadMarkets()
    }
}