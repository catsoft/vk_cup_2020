package com.c.v.ui.check_in_flow.profile

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
import com.c.v.databinding.FragmentProfileBinding
import com.c.v.ui.auth.AuthViewModel
import com.c.v.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class ProfileFragment : StateFragment<FragmentProfileBinding>() {
    private val viewModel: ProfileViewModel by viewModels()

    private val authViewModel : AuthViewModel by activityViewModels()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding = FragmentProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        authViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (!it) {
                findNavController().navigate(R.id.navigation_auth)
            }
        })

        RxView.clicks(viewBinding.toPlacesButton).subscribe {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToNavigationPlaces())
        }.addTo(compositeDisposable)
    }
}

