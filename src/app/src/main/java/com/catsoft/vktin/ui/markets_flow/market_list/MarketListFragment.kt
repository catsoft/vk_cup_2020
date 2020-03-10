package com.catsoft.vktin.ui.markets_flow.market_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.contains
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktin.MainActivity
import com.catsoft.vktin.R
import com.catsoft.vktin.databinding.FragmentMarketListBinding
import com.catsoft.vktin.ui.base.StateFragment
import com.catsoft.vktin.ui.markets_flow.city_selecting.CitySelectingFragment
import com.catsoft.vktin.ui.markets_flow.city_selecting.IOnSelectCityCallback
import com.catsoft.vktin.vkApi.model.VKCity
import io.reactivex.rxkotlin.addTo

class MarketListFragment : StateFragment<FragmentMarketListBinding>(), IOnSelectCityCallback {

    private lateinit var viewModel: MarketListViewModel
    private var dropDownImage : ImageView? = null

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentMarketListBinding = FragmentMarketListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MarketListViewModel::class.java)

        subscribeToState(viewModel)

        initList()

        initToolbar()
    }

    private fun initList() {
        val adapter = MarketListRecyclerViewAdapter(activity!!)
        val list = viewBinding.marketListRecyclerView
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.groups.subscribe {
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
        }.addTo(compositeDisposable)
    }

    private fun initToolbar() {

        val toolbar = (requireActivity() as MainActivity).viewBinding.toolbar

        viewModel.selectedCityObservable.subscribe {
            toolbar.title = if (it?.title?.isNotEmpty() != true) "Магазины" else "Магазины в ${it.title}"
        }.addTo(compositeDisposable)

        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                clearToolbar()

                dropDownImage = ImageView(activity!!)
                dropDownImage?.setImageResource(R.drawable.ic_dropdown)

                toolbar.addView(dropDownImage)

                toolbar.setOnClickListener {
                    val dialogFragment = CitySelectingFragment(this, viewModel.selectedCity!!)
                    dialogFragment.show(activity!!.supportFragmentManager, "signature")
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()

        clearToolbar()
    }

    private fun clearToolbar() {
        val toolbar = (requireActivity() as MainActivity).viewBinding.toolbar
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