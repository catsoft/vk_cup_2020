package com.catsoft.vktinG.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.catsoft.vktinG.MainActivity
import com.catsoft.vktinG.R
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.CurrentLocaleProvider
import com.catsoft.vktinG.vkApi.model.VKProduct
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product.*
import java.util.*

class ProductFragment : Fragment() {

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments!!
        val item = bundle.getParcelable<VKProduct>("item")!!
        (activity as MainActivity).toolbar!!.title = item.title

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        viewModel.setProduct(item)

        viewModel.product.error.observe(this as LifecycleOwner, Observer {
            if(it != null) {
                Toast.makeText(activity, "Произошла ошибка", Toast.LENGTH_LONG).show()
            }
        })

        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale

        viewModel.product.data.observe(this as LifecycleOwner, Observer {

            if (it == null) return@Observer

            Glide.with(this)
                .load(it.thumb_photo)
                .into(image!!)

            title!!.text = it.title

            val currency = Currency.getInstance(item.price.currency.name)
            val currencyFormatter = java.text.NumberFormat.getCurrencyInstance(locale)
            currencyFormatter.maximumFractionDigits = 0
            currencyFormatter.currency = currency
            val price = currencyFormatter.format(item.price.amount)

            this.price!!.text = price

            this.description!!.text = it.description
        })

        viewModel.isFavorite.observe(this as LifecycleOwner, Observer {
            when(it){
                null -> {
                    addButton.visibility = View.GONE
                    removeButton.visibility = View.GONE
                }
                true -> {
                    addButton.visibility = View.GONE
                    removeButton.visibility = View.VISIBLE
                }
                false -> {
                    addButton.visibility = View.VISIBLE
                    removeButton.visibility = View.GONE
                }
            }
        })

        addButton.setOnClickListener {
            viewModel.toggleIsFavorite()
        }

        removeButton.setOnClickListener {
            viewModel.toggleIsFavorite()
        }
    }
}