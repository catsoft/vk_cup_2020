package com.catsoft.vktin.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktin.R
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.WindowsInsetProvider
import com.catsoft.vktin.ui.base.StateDialogFragment
import com.catsoft.vktin.vkApi.model.VKCity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_cities_select.*
import kotlinx.android.synthetic.main.fragment_cities_select.view.*

class CitiesSelectListFragment(
    private val onSelectCallback: IOnSelectCityCallback,
    private val selectedCity : VKCity
) : StateDialogFragment() {

    private lateinit var viewModel: CitiesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_cities_select, container, false)
    }

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

        RxView.clicks(dismiss_image).subscribe { this.dismiss() }.addTo(compositeDisposable)
    }

    private fun initList() {
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnim
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val insets = SimpleDi.Instance.resolve<WindowsInsetProvider>(WindowsInsetProvider::class.java).insets
        val layout = view!!.root_dialog.layoutParams as LinearLayoutCompat.LayoutParams
        layout.topMargin = insets.top
        layout.bottomMargin = insets.bottom
        view!!.root_dialog.layoutParams = layout

        val adapter = CitiesListRecyclerViewAdapter(onSelectCallback, selectedCity)
        val list = cities_list_recycler_view
        val layoutManager = LinearLayoutManager(view!!.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.cities.subscribeBy {
            adapter.update(it)
        }.addTo(compositeDisposable)
    }
}
