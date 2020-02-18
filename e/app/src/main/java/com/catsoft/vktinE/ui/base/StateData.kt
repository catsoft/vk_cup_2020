package com.catsoft.vktinE.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class StateData<T> {

    protected val mutableStatus = MutableLiveData<DataStatus>()
    val status: LiveData<DataStatus> = mutableStatus

    protected val mutableData = MutableLiveData<T>()
    val data: LiveData<T> = mutableData

    protected val mutableError = MutableLiveData<Throwable?>()
    val error: LiveData<Throwable?> = mutableError

    init {
        mutableStatus.value = DataStatus.CREATED
        mutableData.value = null
        mutableError.value = null
    }

    enum class DataStatus {
        CREATED, SUCCESS, ERROR, LOADING
    }
}