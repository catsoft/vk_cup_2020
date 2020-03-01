package com.catsoft.vktinG.ui.base

import androidx.fragment.app.DialogFragment
import io.reactivex.disposables.CompositeDisposable

open class DisposableDialogFrament() : DialogFragment() {
    protected var compositeDisposable = CompositeDisposable()
        private set


    override fun onPause() {
        super.onPause()

        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }
}