package com.catsoft.vktin.ui.base

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