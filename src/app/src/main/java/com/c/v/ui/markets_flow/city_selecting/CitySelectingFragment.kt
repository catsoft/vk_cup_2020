package com.c.v.ui.markets_flow.city_selecting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.v.databinding.FragmentCitySelectingBinding
import com.c.v.databinding.FragmentsStatesEmptyBinding
import com.c.v.databinding.FragmentsStatesErrorBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.ui.base.StateDialogFragment
import com.c.v.data.network.vkApi.model.VKCity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class CitySelectingFragment : StateDialogFragment<FragmentCitySelectingBinding>() {

    private val viewModel: CitySelectingViewModel by viewModels()

    private val args: CitySelectingFragmentArgs by navArgs()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentCitySelectingBinding =
        FragmentCitySelectingBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.statesEmpty

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.statesLoading

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.statesError

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        initList(args.selectedCity)

        RxView.clicks(viewBinding.dismissImage).subscribe { findNavController().navigateUp() }.addTo(compositeDisposable)
    }

    private fun initList(selectedCity: VKCity) {
        val adapter = CityListRecyclerViewAdapter(selectedCity, findNavController())
        val list = viewBinding.citiesListRecyclerView
        val layoutManager = LinearLayoutManager(view!!.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.cities.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
    }
}