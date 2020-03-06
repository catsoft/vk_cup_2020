package com.catsoft.vktin.ui.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.catsoft.vktin.R
import com.catsoft.vktin.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_features.*

class FeaturesFragment : StateFragment() {
    private lateinit var viewModel: FeaturesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_features, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeaturesViewModel::class.java)

        subscribeToState(viewModel)

        viewModel.init()

        RxView.clicks(features_products_button).subscribe {
            findNavController().navigate(R.id.navigation_markets)
        }.addTo(compositeDisposable)

        RxView.clicks(features_unsubscribe_button).subscribe {
            findNavController().navigate(R.id.navigation_groupsList)
        }.addTo(compositeDisposable)

        RxView.clicks(features_share_button).subscribe {
            findNavController().navigate(R.id.navigation_share)
        }.addTo(compositeDisposable)

        RxView.clicks(features_documents_button).subscribe {
            findNavController().navigate(R.id.navigation_documents)
        }.addTo(compositeDisposable)

        viewModel.start()
    }
}

