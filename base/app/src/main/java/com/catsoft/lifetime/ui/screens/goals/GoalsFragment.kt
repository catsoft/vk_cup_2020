package com.catsoft.lifetime.ui.screens.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catsoft.lifetime.R
import com.catsoft.lifetime.presentation.goals.GoalsViewModel
import com.catsoft.lifetime.ui.screens.base.BaseFragment

class GoalsFragment : BaseFragment<GoalsViewModel>(R.layout.fragment_goals, GoalsViewModel::class.java) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(layoutInflater, container, savedInstanceState)!!
    }
}