package com.catsoft.vktinF.ui.base

import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable

open class DisposableDialogFrament() : BottomSheetDialogFragment() {
    protected var compositeDisposable = CompositeDisposable()
        private set


    override fun onPause() {
        super.onPause()

        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }
}