package com.catsoft.lifetime.ui.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.catsoft.lifetime.di.Injectable
import com.catsoft.lifetime.presentation.base.BaseViewModel
import com.catsoft.lifetime.ui.base.TimeApplication

open class BaseFragment<T : BaseViewModel>(private val rootResourceId: Int, private val viewModelType: Class<T>) : Fragment(), Injectable {
    lateinit var viewModel: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this)[viewModelType]

        return inflater.inflate(rootResourceId, container, false)
    }
}