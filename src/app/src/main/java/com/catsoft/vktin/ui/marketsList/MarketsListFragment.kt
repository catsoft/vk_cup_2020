package com.catsoft.vktin.ui.marketsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.contains
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktin.MainActivity
import com.catsoft.vktin.R
import com.catsoft.vktin.ui.base.StateFragment
import com.catsoft.vktin.ui.cities.CitiesSelectListFragment
import com.catsoft.vktin.ui.cities.IOnSelectCityCallback
import com.catsoft.vktin.vkApi.model.VKCity
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

        initList()

        initToolbar()

        viewModel.start()
    }

    private fun initList() {
        val adapter = MarketsListRecyclerViewAdapter(activity!!)
        val list = market_list_recycler_view
        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.groups.subscribe {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
        }.addTo(compositeDisposable)
    }

    private fun initToolbar() {

        val toolbar = (activity as MainActivity).toolbar!!

        viewModel.selectedCityObservable.subscribe {
            toolbar.title = if (it?.title?.isNotEmpty() != true) "Магазины" else "Магазины в ${it.title}"
        }.addTo(compositeDisposable)

        viewModel.isSuccess.filter { it }.subscribe {
            clearToolbar()

            dropDownImage = ImageView(activity!!)
            dropDownImage?.setImageResource(R.drawable.ic_dropdown)

            toolbar.addView(dropDownImage)

            toolbar.setOnClickListener {
                val dialogFragment = CitiesSelectListFragment(this, viewModel.selectedCity!!)
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