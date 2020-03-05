package com.catsoft.vktin.ui.base

import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_groups.*
import kotlinx.android.synthetic.main.fragments_states_empty.*
import kotlinx.android.synthetic.main.fragments_states_error.*
import kotlinx.android.synthetic.main.fragments_states_loading.*

open class StateFragment : Fragment() {

    protected var compositeDisposable = CompositeDisposable()
        private set

    fun subscribeToState(viewModel: BaseViewModel) {
        viewModel.isError.subscribe {
            if (errorState_root != null) {
                errorState_root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.error.subscribe {
            if (errorState_text != null) {
                errorState_text.text = it?.toString()
            }
        }.addTo(compositeDisposable)

        viewModel.isProgress.subscribe {
            if (loadingState_root != null) {
                loadingState_root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.isSuccess.subscribe {
            if (normal_state != null) {
                normal_state.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.isEmpty.subscribe {
            if (emptyState_root != null) {
                emptyState_root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }
}

