package com.catsoft.vktin.ui.markets_flow.market_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.contains
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktin.MainActivity
import com.catsoft.vktin.R
import com.catsoft.vktin.databinding.FragmentMarketListBinding
import com.catsoft.vktin.databinding.FragmentsStatesEmptyBinding
import com.catsoft.vktin.databinding.FragmentsStatesErrorBinding
import com.catsoft.vktin.databinding.FragmentsStatesLoadingBinding
import com.catsoft.vktin.ui.base.StateFragment
import com.catsoft.vktin.vkApi.model.VKCity
import io.reactivex.rxkotlin.addTo

class MarketListFragment : StateFragment<FragmentMarketListBinding>() {

    private val viewModel: MarketListViewModel by viewModels()
    private var dropDownImage: ImageView? = null

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentMarketListBinding = FragmentMarketListBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.fragmentsStatesEmpty

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.fragmentsStatesLoading

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.fragmentsStatesError

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        initList()

        initToolbar()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<VKCity>("selectedCity")!!.observe(viewLifecycleOwner, Observer {
            viewModel.selectCity(it)
        })
    }

    private fun initList() {
        val adapter = MarketListRecyclerViewAdapter(activity!!)
        val list = viewBinding.marketListRecyclerView
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.groups.observe(viewLifecycleOwner, Observer { adapter.updateMarketsListItems(it) })
    }

    private fun initToolbar() {

        val toolbar = (requireActivity() as MainActivity).viewBinding.toolbar

        viewModel.selectedCity.observe(viewLifecycleOwner, Observer {
            toolbar.title = if (it?.title?.isNotEmpty() != true) "Магазины" else "Магазины в ${it.title}"
        })

        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                clearToolbar()

                dropDownImage = ImageView(activity!!)
                dropDownImage?.setImageResource(R.drawable.ic_dropdown)

                toolbar.addView(dropDownImage)

                toolbar.setOnClickListener {
                    val action = MarketListFragmentDirections.actionNavigationMarketsToNavigationCitySelecting(viewModel.selectedCity.value!!)
                    findNavController().navigate(action)
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
}