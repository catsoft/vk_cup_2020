package com.catsoft.vktin.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.catsoft.vktin.MainActivity
import com.catsoft.vktin.R
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.CurrentLocaleProvider
import com.catsoft.vktin.ui.base.StateFragment
import com.catsoft.vktin.vkApi.model.VKProduct
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product.*
import java.text.NumberFormat
import java.util.*

class ProductFragment : StateFragment() {

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

        subscribeToState(viewModel)

        viewModel.init()

        initProduct(item)

        initIsFavorite()

        setupButtons()

        viewModel.start(item)
    }

    private fun setupButtons() {
        RxView.clicks(addButton).subscribe { viewModel.toggleIsFavorite() }.addTo(compositeDisposable)
        RxView.clicks(removeButton).subscribe { viewModel.toggleIsFavorite() }.addTo(compositeDisposable)

        addButton.visibility = View.GONE
        removeButton.visibility = View.GONE
    }

    private fun initProduct(item: VKProduct) {
        viewModel.product.subscribe {

            if (it != null) {
                Glide.with(this).load(it.thumb_photo).into(image!!)

                title!!.text = it.title

                val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
                val currency = Currency.getInstance(item.price.currency.name)
                val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
                currencyFormatter.maximumFractionDigits = 0
                currencyFormatter.currency = currency
                val price = currencyFormatter.format(item.price.amount)

                this.price!!.text = price

                this.description!!.text = it.description
            }

        }.addTo(compositeDisposable)
    }

    private fun initIsFavorite() {
        viewModel.isFavorite.observeOn(AndroidSchedulers.mainThread()).subscribe {
            when (it) {
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
        }.addTo(compositeDisposable)
    }
}