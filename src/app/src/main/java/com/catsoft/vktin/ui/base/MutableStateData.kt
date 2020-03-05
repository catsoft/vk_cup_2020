package com.catsoft.vktin.ui.base

class MutableStateData<T> : StateData<T>() {
    fun loading(): MutableStateData<T> {
        mutableStatus.value = DataStatus.LOADING
        mutableData.value = null
        mutableError.value = null
        return this
    }

    fun success(data: T): MutableStateData<T> {
        mutableStatus.value = DataStatus.SUCCESS
        mutableData.value = data
        mutableError.value = null
        return this
    }

    fun error(error: Throwable?): MutableStateData<T> {
        mutableStatus.value = DataStatus.ERROR
        mutableData.value = null
        mutableError.value = error
        return this
    }
}