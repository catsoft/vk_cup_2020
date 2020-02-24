package com.catsoft.vktinG.ui.marketsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.contains
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktinG.MainActivity
import com.catsoft.vktinG.R
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.CurrentLocaleProvider
import com.catsoft.vktinG.ui.base.StateFragment
import com.catsoft.vktinG.ui.cities.CitiesSelectListFragment
import com.catsoft.vktinG.ui.cities.IOnSelectCityCallback
import com.catsoft.vktinG.vkApi.model.VKCity
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_markets.*

class MarketsListFragment : StateFragment(), IOnSelectCityCallback {

    private lateinit var viewModel: MarketsListViewModel
    private var dropDownImage : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_markets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MarketsListViewModel::class.java)

        subscribeToState(viewModel)

        viewModel.init()

        initList(view)

        initToolbar()

        viewModel.start()
    }

    private fun initList(view: View) {
        val adapter = MarketsListRecyclerViewAdapter(activity!!)
        val list = market_list_recycler_view
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.viewGroups.subscribe {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
        }.addTo(compositeDisposable)
    }

    private fun initToolbar() {

        val toolbar = (activity as MainActivity).toolbar!!

        viewModel.selectedCityObserver.subscribe {
            toolbar.title = if (it?.title?.isNotEmpty() != true) "Магазины" else "Магазины в ${it.title}"
        }.addTo(compositeDisposable)

        viewModel.isSuccess.filter { it }.subscribe {
            clearToolbar()

            dropDownImage = ImageView(activity!!)
            dropDownImage?.setImageResource(R.drawable.ic_dropdown)

            toolbar.addView(dropDownImage)

            toolbar.setOnClickListener {
                val dialogFragment = CitiesSelectListFragment(this, viewModel.citiesList, viewModel.selectedCity!!)
                dialogFragment.show(activity!!.supportFragmentManager, "signature")
            }
        }.addTo(compositeDisposable)
    }

    override fun onPause() {
        super.onPause()

        clearToolbar()
    }

    private fun clearToolbar() {
        val toolbar = (activity as MainActivity).toolbar!!
        dropDownImage?.let {
            if (toolbar.contains(it)) {
                toolbar.removeView(it)
            }
        }

        toolbar.setOnClickListener { }
    }

    override fun select(city: VKCity) {
        viewModel.selectCity(city)
    }
}