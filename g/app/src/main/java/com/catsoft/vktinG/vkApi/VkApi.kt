package com.catsoft.vktinG.vkApi

import com.catsoft.vktinG.vkApi.model.VKCity
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

    override fun getProduct(ownerId: Int, idProduct: Int): Observable<VKProduct> = Observable.create<VKProduct> {
        VK.execute(VKGetProductRequest(ownerId, idProduct), VKApiEmittedCallback<VKProduct>(it))
    }

    override fun getCitiesList(): Observable<List<VKCity>> = Observable.create<List<VKCity>> {
        VK.execute(VKGetCityListRequest(), VKApiEmittedCallback<List<VKCity>>(it))
    }
}
