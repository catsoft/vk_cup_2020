package com.catsoft.vktin.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<TBinding : ViewBinding> : Fragment() {
    private var _viewBinding: TBinding? = null
    protected val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _viewBinding = getViewBindingInflater()(inflater, container, false)
        return viewBinding.root
    }

    abstract fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> TBinding

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}

