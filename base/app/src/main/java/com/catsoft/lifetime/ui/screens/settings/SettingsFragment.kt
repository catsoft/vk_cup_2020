package com.catsoft.lifetime.ui.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catsoft.lifetime.R
import com.catsoft.lifetime.presentation.settings.SettingsViewModel
import com.catsoft.lifetime.ui.screens.base.BaseFragment

class SettingsFragment : BaseFragment<SettingsViewModel>(R.layout.fragment_settings, SettingsViewModel::class.java) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(layoutInflater, container, savedInstanceState)!!
    }
}
