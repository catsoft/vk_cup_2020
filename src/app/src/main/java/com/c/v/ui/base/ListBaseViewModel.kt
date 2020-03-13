package com.c.v.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class ListBaseViewModel<TItem> : BaseViewModel() {

    protected val mutableList = MutableLiveData<List<TItem>>()
    val list: LiveData<List<TItem>> = mutableList

    open fun whenLoadList(list: List<TItem>) {
        setListData(list, mutableList)
    }
}