package com.catsoft.vktinG.ui.base

import android.content.res.TypedArray
import androidx.appcompat.app.WindowDecorActionBar
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import java.util.*

abstract class BaseAdapter<T : RecyclerView.ViewHolder, TItem> : RecyclerView.Adapter<T>() {

    protected var items = mutableListOf<TItem>()

    protected var compositeDisposable = CompositeDisposable()

    override fun getItemCount(): Int = items.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }
}