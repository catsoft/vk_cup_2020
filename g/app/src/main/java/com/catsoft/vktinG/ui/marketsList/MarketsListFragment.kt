package com.catsoft.vktinG.ui.marketsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktinG.MainActivity
import com.catsoft.vktinG.R
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.CurrentLocaleProvider
import com.catsoft.vktinG.ui.cities.CitiesSelectListFragment
import com.catsoft.vktinG.ui.cities.IOnSelectCallback
import com.catsoft.vktinG.vkApi.model.VKCity
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_markets.*

class MarketsListFragment : Fragment(), IOnSelectCallback {

    private lateinit var viewModel: MarketsListViewModel
    private lateinit var dropDownImage : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_markets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MarketsListViewModel::class.java)

        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = MarketsListRecyclerViewAdapter(locale, viewModel, activity!!)
        val list = market_list_recycler_view
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.viewGroups.subscribe{
            if (it != null) {
                adapter.updateMarketsListItems(it)
            }
        }.addTo(viewModel.compositeDisposable)

        viewModel.load()

        val toolbar = (activity as MainActivity).toolbar!!

        viewModel.cityPublisher.subscribe {
            toolbar.title = if (it?.title?.isNotEmpty() != true) "Магазины" else "Магазины в ${it.title}"
        }.addTo(viewModel.compositeDisposable)

        dropDownImage = ImageView(activity!!)
        dropDownImage.setImageResource(R.drawable.ic_dropdown)
        toolbar.addView(dropDownImage)

        toolbar.setOnClickListener {
            val dialogFragment = CitiesSelectListFragment(this, viewModel.citiesList, viewModel.selectedCity)
            dialogFragment.show(activity!!.supportFragmentManager, "signature")
        }
    }

    override fun onPause() {
        super.onPause()

        val toolbar = (activity as MainActivity).toolbar!!
        toolbar.removeView(dropDownImage)
    }

    override fun select(city: VKCity) {
        viewModel.selectCity(city)
    }
}