package com.catsoft.lifetime.ui.screens.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catsoft.lifetime.R
import com.catsoft.lifetime.presentation.graphs.GraphsViewModel
import com.catsoft.lifetime.ui.screens.base.BaseFragment

class GraphsFragment : BaseFragment<GraphsViewModel>(R.layout.fragment_graphs, GraphsViewModel::class.java)  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(layoutInflater, container, savedInstanceState)!!
    }
}