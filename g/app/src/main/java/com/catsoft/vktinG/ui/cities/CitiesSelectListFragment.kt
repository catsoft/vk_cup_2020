package com.catsoft.vktinG.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktinG.R
import com.catsoft.vktinG.vkApi.model.VKCity
import kotlinx.android.synthetic.main.fragment_cities_select.*

class CitiesSelectListFragment(
    private val onSelectCallback: IOnSelectCallback,
    private val cities: List<VKCity>,
    private val selectedCity : VKCity
) : DialogFragment() {


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

        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnim;

        val adapter = CitiesListRecyclerViewAdapter(onSelectCallback, cities, selectedCity)
        val list = cities_list_recycler_view
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        dismiss_image.setOnClickListener {
            this.dismiss()
        }
    }
}
