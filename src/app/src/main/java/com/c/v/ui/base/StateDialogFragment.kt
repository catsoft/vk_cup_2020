package com.c.v.ui.base

import android.view.View
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.c.v.databinding.FragmentsStatesEmptyBinding
import com.c.v.databinding.FragmentsStatesErrorBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.utils.observe
import io.reactivex.disposables.CompositeDisposable

abstract class StateDialogFragment<TViewBinding : ViewBinding> : ViewBindingDialogFragment<TViewBinding>() {

    protected var compositeDisposable = CompositeDisposable()
        private set

    open fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = null

    open fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = null

    open fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = null

    open fun getNormalStateView(): View? = null

    protected fun subscribeToState(viewModel: BaseViewModel) {
        observe(viewModel.isError) {
            val errorState = getErrorStateViewBinding()
            if (errorState != null) {
                errorState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        observe(viewModel.error) {
            val errorState = getErrorStateViewBinding()
            if (errorState != null) {
                errorState.errorStateText.text = it.toString()
            }
        }

        observe(viewModel.isProgress) {
            val loadingState = getLoadingStateViewBinding()
            if (loadingState != null) {
                loadingState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        observe(viewModel.isSuccess) {
            val normalState = getNormalStateView()
            if (normalState != null) {
                normalState.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        observe(viewModel.isEmpty) {
            val emptyState = getEmptyStateViewBinding()
            if (emptyState != null) {
                emptyState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }
}