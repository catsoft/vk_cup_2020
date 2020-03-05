package com.catsoft.vktin.vkApi.requests

import com.vk.api.sdk.requests.VKRequest

open class VKBaseRequest<T>(method: String) : VKRequest<T>(method) {
    init {
        addParam("v", "5.103")
    }
}