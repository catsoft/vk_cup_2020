package com.catsoft.vktinG.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktinG.R
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.WindowsInsetProvider
import com.catsoft.vktinG.ui.base.DisposableDialogFrament
import com.catsoft.vktinG.vkApi.model.VKCity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cities_select.*
import kotlinx.android.synthetic.main.fragment_cities_select.view.*

class CitiesSelectListFragment(
    private val onSelectCallback: IOnSelectCityCallback,
    private val cities: List<VKCity>,
    private val selectedCity : VKCity
) : DisposableDialogFrament() {

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

        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnim
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        var insets = SimpleDi.Instance.resolve<WindowsInsetProvider>(WindowsInsetProvider::class.java).insets
        val layout = view.root_dialog.layoutParams as LinearLayoutCompat.LayoutParams
        layout.topMargin = insets.top
        view.root_dialog.layoutParams = layout

        val adapter = CitiesListRecyclerViewAdapter(onSelectCallback, cities, selectedCity)
        val list = cities_list_recycler_view
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        RxView.clicks(dismiss_image).subscribe { this.dismiss() }.addTo(compositeDisposable)
    }
}
