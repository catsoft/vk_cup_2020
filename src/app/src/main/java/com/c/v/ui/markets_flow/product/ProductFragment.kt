package com.c.v.ui.markets_flow.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.c.v.MainActivity
import com.c.v.databinding.FragmentProductBinding
import com.c.v.databinding.FragmentsStatesEmptyBinding
import com.c.v.databinding.FragmentsStatesErrorBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.di.SimpleDi
import com.c.v.services.CurrentLocaleProvider
import com.c.v.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import java.text.NumberFormat
import java.util.*

class ProductFragment : StateFragment<FragmentProductBinding>() {

    private val viewModel: ProductViewModel by viewModels()

    private val args: ProductFragmentArgs by navArgs()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductBinding = FragmentProductBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.statesEmpty

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.statesLoading

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.statesError

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).viewBinding.toolbar.title = args.selectedProduct.title
        viewModel.start(args.selectedProduct)
        subscribeToState(viewModel)

        initProduct()

        initIsFavorite()

        setupButtons()
    }

    private fun setupButtons() {
        RxView.clicks(viewBinding.addButton).subscribe { viewModel.toggleIsFavorite() }.addTo(compositeDisposable)
        RxView.clicks(viewBinding.removeButton).subscribe { viewModel.toggleIsFavorite() }.addTo(compositeDisposable)

        viewBinding.addButton.visibility = View.GONE
        viewBinding.removeButton.visibility = View.GONE
    }

    private fun initProduct() {
        viewModel.product.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            Glide.with(this).load(it.thumb_photo).into(viewBinding.image)

            viewBinding.title.text = it.title

            val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
            val currency = Currency.getInstance(it.price.currency.name)
            val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
            currencyFormatter.maximumFractionDigits = 0
            currencyFormatter.currency = currency
            val price = currencyFormatter.format(it.price.amount)

            viewBinding.price.text = price

            viewBinding.description.text = it.description
        })
    }

    private fun initIsFavorite() {
        viewModel.isFavorite.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                true -> {
                    viewBinding.addButton.visibility = View.GONE
                    viewBinding.removeButton.visibility = View.VISIBLE
                }
                false -> {
                    viewBinding.addButton.visibility = View.VISIBLE
                    viewBinding.removeButton.visibility = View.GONE
                }
            }
        })
    }
}