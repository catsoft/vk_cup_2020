package com.c.v.utils

import android.database.Observable
import android.view.View
import androidx.core.view.doOnDetach
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.subjects.PublishSubject

fun Boolean.toVisibility() : Int {
    return if (this) View.VISIBLE else View.GONE
}

fun <T> Fragment.observe(liveData : LiveData<T>, action : (t:T) -> Unit) {
    liveData.observe(this.viewLifecycleOwner, Observer { action.invoke(it) })
}

fun SwipeRefreshLayout.onRefreshObserver() : io.reactivex.Observable<Int> {
    val publisher = PublishSubject.create<Int>()

    val listener = SwipeRefreshLayout.OnRefreshListener {
        publisher.onNext(1)
    }
    this.setOnRefreshListener(listener)
    this.doOnDetach { this.setOnRefreshListener { } }

    return publisher
}

fun <T>MutableLiveData<T>.repost() {
    this.postValue(this.value!!)
}