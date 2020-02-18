package com.catsoft.vktinE.vkApi.documents

import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import io.reactivex.ObservableEmitter

class VKApiEmittedCallback<T>(private val emitter: ObservableEmitter<T>) :
    VKApiCallback<T> {
    override fun fail(error: VKApiExecutionException) {
        emitter.tryOnError(error)
    }

    override fun success(result: T) {
        emitter.onNext(result)
    }
}