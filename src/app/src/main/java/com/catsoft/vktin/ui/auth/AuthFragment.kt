package com.catsoft.vktin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.catsoft.vktin.R
import com.catsoft.vktin.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : StateFragment() {
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        viewModel.init()

        RxView.clicks(auth_button).subscribe {
            VK.login(activity!!, setOf(VKScope.GROUPS, VKScope.MARKET, VKScope.WALL, VKScope.DOCS))
        }.addTo(compositeDisposable)

        viewModel.isError.subscribe {
            auth_error.visibility = if (it) View.VISIBLE else View.GONE
        }.addTo(compositeDisposable)

        viewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigateUp()
            }
        })

        viewModel.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, viewModel.vkCallback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

