package com.catsoft.vktin.vkApi

import com.vk.api.sdk.VKApiCallback
import io.reactivex.ObservableEmitter

class VKApiEmittedCallback<T>(private val emitter: ObservableEmitter<T>) : VKApiCallback<T> {
    override fun fail(error: Exception) {
        emitter.onError(error)
    }

    override fun success(result: T) {
        emitter.onNext(result)
    }
}