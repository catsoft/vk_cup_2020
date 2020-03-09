package com.catsoft.vktin.ui.markets_flow.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.catsoft.vktin.MainActivity
import com.catsoft.vktin.databinding.FragmentProductBinding
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.CurrentLocaleProvider
import com.catsoft.vktin.ui.base.StateFragment
import com.catsoft.vktin.vkApi.model.VKProduct
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import java.text.NumberFormat
import java.util.*

class ProductFragment : StateFragment<FragmentProductBinding>() {

    private lateinit var viewModel: ProductViewModel

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductBinding = FragmentProductBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments!!
        val item = bundle.getParcelable<VKProduct>("item")!!
        (requireActivity() as MainActivity).viewBinding.toolbar.title = item.title

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        subscribeToState(viewModel)

        viewModel.init()

        initProduct(item)

        initIsFavorite()

        setupButtons()

        viewModel.start(item)
    }

    private fun setupButtons() {
        RxView.clicks(viewBinding.addButton).subscribe { viewModel.toggleIsFavorite() }.addTo(compositeDisposable)
        RxView.clicks(viewBinding.removeButton).subscribe { viewModel.toggleIsFavorite() }.addTo(compositeDisposable)

        viewBinding.addButton.visibility = View.GONE
        viewBinding.removeButton.visibility = View.GONE
    }

    private fun initProduct(item: VKProduct) {
        viewModel.product.subscribe {

            if (it != null) {
                Glide.with(this).load(it.thumb_photo).into(viewBinding.image)

                viewBinding.title.text = it.title

                val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
                val currency = Currency.getInstance(item.price.currency.name)
                val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
                currencyFormatter.maximumFractionDigits = 0
                currencyFormatter.currency = currency
                val price = currencyFormatter.format(item.price.amount)

                viewBinding.price.text = price

                viewBinding.description.text = it.description
            }

        }.addTo(compositeDisposable)
    }

    private fun initIsFavorite() {
        viewModel.isFavorite.observeOn(AndroidSchedulers.mainThread()).subscribe {
            when (it) {
                null -> {
                    viewBinding.addButton.visibility = View.GONE
                    viewBinding.removeButton.visibility = View.GONE
                }
                true -> {
                    viewBinding.addButton.visibility = View.GONE
                    viewBinding.removeButton.visibility = View.VISIBLE
                }
                false -> {
                    viewBinding.addButton.visibility = View.VISIBLE
                    viewBinding.removeButton.visibility = View.GONE
                }
            }
        }.addTo(compositeDisposable)
    }
}