package com.catsoft.vktinG.ui.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catsoft.vktinG.R
import com.catsoft.vktinG.vkApi.model.VKCity

class CitiesListRecyclerViewAdapter(
    private val onSelectCallback: IOnSelectCallback,
    private var cities: List<VKCity> = mutableListOf(),
    private var selectedCity: VKCity
) : RecyclerView.Adapter<CityViewHolder>() {

    override fun getItemCount(): Int = cities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_city, parent, false)

        val holder = CityViewHolder(view)

        holder.itemView.setOnClickListener {
            val item = cities[holder.adapterPosition]

            onSelectCallback.select(item)

            selectedCity = item

            notifyDataSetChanged()
        }

        return holder
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        val item = cities[position]

        holder.title.text = if (item.title.isEmpty()) "Без города" else item.title

        holder.checkImage.visibility = if(item == selectedCity) View.VISIBLE else View.GONE
    }
}

