package com.catsoft.vktin.vkApi

import android.net.Uri
import com.catsoft.vktin.vkApi.requests.VKWallPostRequest
import com.vk.api.sdk.VK
import io.reactivex.Observable

class VKWallApi : IVKWallApi {
    override fun post(string: String, images: List<Uri>): Observable<Int> = Observable.create<Int> {
        VK.execute(VKWallPostRequest(string, images, 141454621), VKApiEmittedCallback<Int>(it))
    }
}