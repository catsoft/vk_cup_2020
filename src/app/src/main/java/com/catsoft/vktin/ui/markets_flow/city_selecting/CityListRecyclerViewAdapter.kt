package com.catsoft.vktin.ui.markets_flow.city_selecting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.catsoft.vktin.databinding.CellCityBinding
import com.catsoft.vktin.ui.base.BaseAdapter
import com.catsoft.vktin.vkApi.model.VKCity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class CityListRecyclerViewAdapter(
    private var selectedCity: VKCity, private val navController: NavController
) : BaseAdapter<CityViewHolder, VKCity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CellCityBinding.inflate(inflater, parent, false)
        val holder = CityViewHolder(binding)

        RxView.clicks(holder.itemView).subscribe {
            val item = items[holder.adapterPosition]
            navController.previousBackStackEntry?.savedStateHandle?.set("selectedCity", item)
            selectedCity = item

            notifyDataSetChanged()
            navController.navigateUp()
        }.addTo(compositeDisposable)

        return holder
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        val item = items[position]

        holder.binding.titleTextView.text = if (item.title.isEmpty()) "Без города" else item.title

        holder.binding.checkIcon.visibility = if (item == selectedCity) View.VISIBLE else View.GONE
    }

    fun update(cities: List<VKCity>) {
        items = cities.toMutableList()
        notifyDataSetChanged()
    }
}
