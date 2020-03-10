package com.catsoft.vktin.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.catsoft.vktin.databinding.FragmentsStatesEmptyBinding
import com.catsoft.vktin.databinding.FragmentsStatesErrorBinding
import com.catsoft.vktin.databinding.FragmentsStatesLoadingBinding
import io.reactivex.disposables.CompositeDisposable

abstract class StateDialogFragment<TViewBinding : ViewBinding> : ViewBindingDialogFragment<TViewBinding>() {

    protected var compositeDisposable = CompositeDisposable()
        private set

    open fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = null

    open fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = null

    open fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = null

    open fun getNormalStateView(): View? = null

    protected fun subscribeToState(viewModel: BaseViewModel) {
        viewModel.isError.observe(this, Observer {
            val errorState = getErrorStateViewBinding()
            if (errorState != null) {
                errorState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.error.observe(this, Observer {
            val errorState = getErrorStateViewBinding()
            if (errorState != null) {
                errorState.errorStateText.text = it?.toString()
            }
        })

        viewModel.isProgress.observe(this, Observer {
            val loadingState = getLoadingStateViewBinding()
            if (loadingState != null) {
                loadingState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.isSuccess.observe(this, Observer {
            val normalState = getNormalStateView()
            if (normalState != null) {
                normalState.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.isEmpty.observe(this, Observer {
            val emptyState = getEmptyStateViewBinding()
            if (emptyState != null) {
                emptyState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }
}