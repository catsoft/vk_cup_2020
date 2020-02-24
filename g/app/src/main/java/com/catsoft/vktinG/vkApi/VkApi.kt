package com.catsoft.vktinG.vkApi

import com.catsoft.vktinG.vkApi.model.VKGroup
import com.catsoft.vktinG.vkApi.model.VKProduct
import com.catsoft.vktinG.vkApi.requests.*
import com.vk.api.sdk.VK
import io.reactivex.Observable

class VkApi : IVkApi {

    override fun getMarketsList(id: Int): Observable<List<VKGroup>> = Observable.create<List<VKGroup>> {
        VK.execute(VKGetGroupListRequest(id), VKApiEmittedCallback<List<VKGroup>>(it))
    }

    override fun getProductsList(idMarket: Int): Observable<List<VKProduct>> = Observable.create<List<VKProduct>> {
        VK.execute(VKGetProductListRequest(idMarket), VKApiEmittedCallback<List<VKProduct>>(it))
    }

    override fun addProductToFavorite(ownerId: Int, idProduct: Int): Observable<Int> = Observable.create<Int> {
        VK.execute(VKAddProductToFavoriteRequest(ownerId, idProduct), VKApiEmittedCallback<Int>(it))
    }

    override fun removeProductFromFavorite(ownerId: Int, idProduct: Int): Observable<Int> = Observable.create<Int> {
        VK.execute(VKRemoveProductFromFavoriteRequest(ownerId, idProduct), VKApiEmittedCallback<Int>(it))
    }

    override fun isLikedProduct(ownerId: Int, idProduct: Int): Observable<Boolean> = Observable.create<Boolean> {
        val i = VK.execute(VKIsLikedRequest(ownerId, idProduct), VKApiEmittedCallback<Boolean>(it))
    }
}
