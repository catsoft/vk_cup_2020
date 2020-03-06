package com.catsoft.vktin.ui.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.catsoft.vktin.R
import com.catsoft.vktin.ui.auth.AuthViewModel
import com.catsoft.vktin.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_features.*

class FeaturesFragment : StateFragment() {
    private val viewModel: FeaturesViewModel by activityViewModels()

    private val authViewModel : AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_features, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        viewModel.init()

        authViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (!it) {
                findNavController().navigate(R.id.navigation_auth)
            }
        })

        RxView.clicks(features_products_button).subscribe {
            findNavController().navigate(FeaturesFragmentDirections.actionNavigationFeaturesToNavigationMarkets())
        }.addTo(compositeDisposable)

        RxView.clicks(features_unsubscribe_button).subscribe {
            findNavController().navigate(FeaturesFragmentDirections.actionNavigationFeaturesToNavigationGroupsList())
        }.addTo(compositeDisposable)

        RxView.clicks(features_share_button).subscribe {
            findNavController().navigate(FeaturesFragmentDirections.actionNavigationFeaturesToNavigationShare())
        }.addTo(compositeDisposable)

        RxView.clicks(features_documents_button).subscribe {
            findNavController().navigate(FeaturesFragmentDirections.actionNavigationFeaturesToNavigationDocuments())
        }.addTo(compositeDisposable)

        viewModel.start()
    }
}

