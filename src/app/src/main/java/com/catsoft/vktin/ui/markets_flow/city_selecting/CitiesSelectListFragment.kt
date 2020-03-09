package com.catsoft.vktin.ui.markets_flow.city_selecting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktin.R
import com.catsoft.vktin.databinding.FragmentCitySelectingBinding
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.WindowsInsetProvider
import com.catsoft.vktin.ui.base.StateDialogFragment
import com.catsoft.vktin.vkApi.model.VKCity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class CitiesSelectListFragment(
    private val onSelectCallback: IOnSelectCityCallback,
    private val selectedCity : VKCity
) : StateDialogFragment<FragmentCitySelectingBinding>() {

    private lateinit var viewModel: CitiesListViewModel

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentCitySelectingBinding = FragmentCitySelectingBinding::inflate

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CitiesListViewModel::class.java)

        subscribeToState(viewModel)

        viewModel.init()

        initList()

        viewModel.start()

        RxView.clicks(viewBinding.dismissImage).subscribe { this.dismiss() }.addTo(compositeDisposable)
    }

    private fun initList() {
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnim
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val insets = SimpleDi.Instance.resolve<WindowsInsetProvider>(WindowsInsetProvider::class.java).insets
        val layout = viewBinding.rootDialog.layoutParams as LinearLayoutCompat.LayoutParams
        layout.topMargin = insets.top
        layout.bottomMargin = insets.bottom
        viewBinding.rootDialog.layoutParams = layout

        val adapter = CitiesListRecyclerViewAdapter(onSelectCallback, selectedCity)
        val list = viewBinding.citiesListRecyclerView
        val layoutManager = LinearLayoutManager(view!!.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.cities.subscribeBy {
            adapter.update(it)
        }.addTo(compositeDisposable)
    }
}
