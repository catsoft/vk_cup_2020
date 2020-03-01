package com.catsoft.vktinG.ui.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catsoft.vktinG.R
import com.catsoft.vktinG.ui.base.BaseAdapter
import com.catsoft.vktinG.vkApi.model.VKCity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class CitiesListRecyclerViewAdapter(
    private val onSelectCallback: IOnSelectCityCallback,
    private var selectedCity: VKCity
) : BaseAdapter<CityViewHolder, VKCity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_city, parent, false)

        val holder = CityViewHolder(view)

        RxView.clicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]

            onSelectCallback.select(item)

            selectedCity = item

            notifyDataSetChanged()
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        val item = items[position]

        holder.title.text = if (item.title.isEmpty()) "Без города" else item.title

        holder.checkImage.visibility = if (item == selectedCity) View.VISIBLE else View.GONE
    }

    fun update(cities : List<VKCity>) {
        items = cities.toMutableList()
        notifyDataSetChanged()
    }
}

