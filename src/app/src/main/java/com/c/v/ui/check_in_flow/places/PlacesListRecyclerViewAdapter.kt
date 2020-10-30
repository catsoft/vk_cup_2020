package com.c.v.ui.check_in_flow.places

import android.content.Context
import android.os.Handler
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.c.v.R
import com.c.v.databinding.CellDocumentBinding
import com.c.v.ui.WithIdDiffCallback
import com.c.v.ui.base.BaseAdapter
import com.c.v.utils.CalendarReadableUtil
import com.c.v.utils.DimensionUtil
import com.c.v.utils.SizeHumanReadableUtil
import com.c.v.data.network.vkApi.model.VKDocument
import com.c.v.data.network.vkApi.model.VKDocumentType
import com.c.v.data.network.vkApi.model.VKPlace
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import java.util.*

class PlacesListRecyclerViewAdapter(
    private val locale: Locale,
    private val viewModel: PlacesListViewModel,
    private val context: Context) : BaseAdapter<PlacesViewHolder, VKPlace>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellDocumentBinding = CellDocumentBinding.inflate(layoutInflater, parent, false)
        val holder = PlacesViewHolder(cellDocumentBinding)

        holder.binding.apply {

            RxView.clicks(root).subscribe {
            }.addTo(compositeDisposable)
        }

        return holder
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {

        val item = items[position]

        holder.binding.apply {
//            nameTextView.setText(title)
//            infoTextView.text = generateInfoString(item)
//            typeContainer.setBackgroundResource(backDrawableRes)
//            typeImage.setImageResource(typeIcon)
//            tagsTextView.text = tagsText

        }
    }

    fun updateDocumentsListItems(documents: List<VKPlace>) {
        val diffResult = DiffUtil.calculateDiff(WithIdDiffCallback(documents, this.items))
        this.items = documents.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}
