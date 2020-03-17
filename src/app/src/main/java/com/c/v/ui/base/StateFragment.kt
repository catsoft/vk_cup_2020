package com.c.v.ui.base

import android.view.View
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.c.v.databinding.FragmentsStatesEmptyBinding
import com.c.v.databinding.FragmentsStatesErrorBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.utils.observe
import com.c.v.utils.toVisibility
import io.reactivex.disposables.CompositeDisposable

abstract class StateFragment<TViewBinding : ViewBinding> : ViewBindingFragment<TViewBinding>() {

    protected var compositeDisposable = CompositeDisposable()
        private set

    open fun getErrorStateViewBinding() : FragmentsStatesErrorBinding? = null

    open fun getEmptyStateViewBinding() : FragmentsStatesEmptyBinding? = null

    open fun getLoadingStateViewBinding() : FragmentsStatesLoadingBinding? = null

    open fun getNormalStateView() : View? = null

    fun subscribeToState(viewModel: BaseViewModel) {
        observe(viewModel.isError) {
            val errorState = getErrorStateViewBinding()
            if (errorState != null) {
                errorState.root.visibility = it.toVisibility()
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
                loadingState.root.visibility = it.toVisibility()
            }
        }

        observe(viewModel.isSuccess) {
            val normalState = getNormalStateView()
            if (normalState != null) {
                normalState.visibility = it.toVisibility()
            }
        }

        observe(viewModel.isEmpty) {
            val emptyState = getEmptyStateViewBinding()
            if (emptyState != null) {
                emptyState.root.visibility = it.toVisibility()
            }
        }
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()

        super.onDestroyView()
    }
}