package com.catsoft.vktinG.vkApi

import com.catsoft.vktinG.vkApi.model.VKGroup
import com.catsoft.vktinG.vkApi.model.VKProduct
import com.catsoft.vktinG.vkApi.requests.VKGetGroupListRequest
import com.catsoft.vktinG.vkApi.requests.VKGetProductListRequest
import com.vk.api.sdk.VK
import io.reactivex.Observable

class VkApi : IVkApi {

    override fun getMarketsList(id:Int): Observable<List<VKGroup>> = Observable.create<List<VKGroup>> {
        VK.execute(VKGetGroupListRequest(id), VKApiEmittedCallback<List<VKGroup>>(it))
    }

    override fun getProductsList(idMarket: Int): Observable<List<VKProduct>> = Observable.create<List<VKProduct>> {
        VK.execute(VKGetProductListRequest(idMarket), VKApiEmittedCallback<List<VKProduct>>(it))
    }
}

