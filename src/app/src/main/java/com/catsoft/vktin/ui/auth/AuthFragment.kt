package com.catsoft.vktin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import com.catsoft.vktin.databinding.FragmentAuthBinding
import com.catsoft.vktin.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import io.reactivex.rxkotlin.addTo

class AuthFragment : StateFragment<FragmentAuthBinding>() {
    private val viewModel: AuthViewModel by activityViewModels()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthBinding = FragmentAuthBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        RxView.clicks(viewBinding.authButton).subscribe {
            VK.login(requireActivity(), setOf(VKScope.GROUPS, VKScope.MARKET, VKScope.WALL, VKScope.DOCS))
        }.addTo(compositeDisposable)

        viewModel.isError.observe(viewLifecycleOwner, Observer {
            viewBinding.authError.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigateUp()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, viewModel.vkCallback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

