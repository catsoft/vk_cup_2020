package com.catsoft.vktin.ui.base

import android.view.View
import androidx.fragment.app.DialogFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_markets.*
import kotlinx.android.synthetic.main.fragments_states_empty.*
import kotlinx.android.synthetic.main.fragments_states_error.*
import kotlinx.android.synthetic.main.fragments_states_loading.*

open class StateDialogFragment : DialogFragment() {

    protected var compositeDisposable = CompositeDisposable()
        private set

    fun subscribeToState(viewModel: BaseViewModel) {
        viewModel.isError.observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (errorState_root != null) {
                errorState_root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.error.observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (errorState_text != null) {
                errorState_text.text = it?.toString()
            }
        }.addTo(compositeDisposable)

        viewModel.isProgress.observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (loadingState_root != null) {
                loadingState_root.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.isSuccess.observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (normal_state != null) {
                normal_state.visibility = if (it) View.VISIBLE else View.GONE
            }
        }.addTo(compositeDisposable)

        viewModel.isEmpty.observeOn(AndroidSchedulers.mainThread()).subscribe {
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