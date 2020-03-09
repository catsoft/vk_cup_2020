package com.catsoft.vktin.ui.base

import android.view.View
import androidx.viewbinding.ViewBinding
import com.catsoft.vktin.databinding.FragmentsStatesEmptyBinding
import com.catsoft.vktin.databinding.FragmentsStatesErrorBinding
import com.catsoft.vktin.databinding.FragmentsStatesLoadingBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

abstract class StateFragment<TViewBinding : ViewBinding> : ViewBindingFragment<TViewBinding>() {

    protected var compositeDisposable = CompositeDisposable()
        private set

    open fun getErrorStateViewBinding() : FragmentsStatesErrorBinding? = null

    open fun getEmptyStateViewBinding() : FragmentsStatesEmptyBinding? = null

    open fun getLoadingStateViewBinding() : FragmentsStatesLoadingBinding? = null

    open fun getNormalStateView() : View? = null

    fun subscribeToState(viewModel: BaseViewModel) {
        viewModel.isError.observeOn(AndroidSchedulers.mainThread()).subscribe {
            val errorState = getErrorStateViewBinding()
            if (errorState != null) {
                errorState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.error.observeOn(AndroidSchedulers.mainThread()).subscribe {
            val errorState = getErrorStateViewBinding()
            if (errorState != null) {
                errorState.errorStateText.text = it?.toString()
            }
        }.addTo(compositeDisposable)

        viewModel.isProgress.observeOn(AndroidSchedulers.mainThread()).subscribe {
            val loadingState = getLoadingStateViewBinding()
            if (loadingState != null) {
                loadingState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.isSuccess.observeOn(AndroidSchedulers.mainThread()).subscribe {
            val normalState = getNormalStateView()
            if (normalState != null) {
                normalState.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.isEmpty.observeOn(AndroidSchedulers.mainThread()).subscribe {
            val emptyState = getEmptyStateViewBinding()
            if (emptyState != null) {
                emptyState.root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }
}

