package com.catsoft.vktin.ui.markets_flow.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.catsoft.vktin.MainActivity
import com.catsoft.vktin.databinding.FragmentProductBinding
import com.catsoft.vktin.databinding.FragmentsStatesEmptyBinding
import com.catsoft.vktin.databinding.FragmentsStatesErrorBinding
import com.catsoft.vktin.databinding.FragmentsStatesLoadingBinding
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