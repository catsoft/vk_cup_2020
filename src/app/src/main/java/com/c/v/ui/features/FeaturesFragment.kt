package com.c.v.ui.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.c.v.R
import com.c.v.databinding.FragmentFeaturesBinding
import com.c.v.ui.auth.AuthViewModel
import com.c.v.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class FeaturesFragment : StateFragment<FragmentFeaturesBinding>() {
    private val viewModel: FeaturesViewModel by viewModels()

    private val authViewModel : AuthViewModel by activityViewModels()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeaturesBinding = FragmentFeaturesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        authViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (!it) {
                findNavController().navigate(R.id.navigation_auth)
            }
        })

        RxView.clicks(viewBinding.featuresProductsButton).subscribe {
            findNavController().navigate(FeaturesFragmentDirections.actionNavigationFeaturesToNavigationMarkets())
        }.addTo(compositeDisposable)

        RxView.clicks(viewBinding.featuresUnsubscribeButton).subscribe {
            findNavController().navigate(FeaturesFragmentDirections.actionNavigationFeaturesToNavigationGroupsList())
        }.addTo(compositeDisposable)

        RxView.clicks(viewBinding.featuresShareButton).subscribe {
            findNavController().navigate(FeaturesFragmentDirections.actionNavigationFeaturesToNavigationPickImage())
        }.addTo(compositeDisposable)

        RxView.clicks(viewBinding.featuresDocumentsButton).subscribe {
            findNavController().navigate(FeaturesFragmentDirections.actionNavigationFeaturesToNavigationDocuments())
        }.addTo(compositeDisposable)
    }
}

