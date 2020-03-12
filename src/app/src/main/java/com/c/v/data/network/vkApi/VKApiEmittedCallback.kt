package com.c.v.data.network.vkApi

import com.vk.api.sdk.VKApiCallback
import io.reactivex.Emitter

class VKApiEmittedCallback<T>(private val emitter: Emitter<T>) : VKApiCallback<T> {
    override fun fail(error: Exception) {
        emitter.onError(error)
    }

    override fun success(result: T) {
        emitter.onNext(result)
    }
}