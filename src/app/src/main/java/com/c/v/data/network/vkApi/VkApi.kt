package com.c.v.data.network.vkApi

import android.net.Uri
import com.c.v.data.network.vkApi.model.*
import com.c.v.data.network.vkApi.requests.*
import com.vk.api.sdk.VK
import com.vk.api.sdk.internal.ApiCommand
import io.reactivex.Observable

class VkApi : IVkApi {

    override fun getMarketList(id: Int): Observable<List<VKGroupApi>> = createObservable(VKSearchGroupListRequest(id))

    override fun getProductList(idMarket: Int): Observable<List<VKProduct>> = createObservable(VKGetProductListRequest(idMarket))

    override fun addProductToFavorite(ownerId: Int, idProduct: Int): Observable<Int> = createObservable(
        VKAddProductToFavoriteRequest(ownerId, idProduct)
    )

    override fun removeProductFromFavorite(ownerId: Int, idProduct: Int): Observable<Int> = createObservable(
        VKRemoveProductFromFavoriteRequest(ownerId, idProduct)
    )

    override fun getProduct(ownerId: Int, idProduct: Int): Observable<VKProduct> = createObservable(VKGetProductRequest(ownerId, idProduct))

    override fun getCityList(): Observable<List<VKCity>> = createObservable(VKGetCityListRequest())

    override fun getGroupList(id: Int): Observable<List<VKGroupApi>> = createObservable(VKGetGroupListRequest(id))

    override fun getLastPost(id: Int): Observable<VKPost?> = createObservable(VKGetLastPostRequest(id))

    override fun getCountFriendsInGroupPost(id: Int): Observable<Int> = createObservable(VKGetFriendsRequest(id))

    override fun groupLeave(id: Int): Observable<Int> = createObservable(VKGroupLeaveRequest(id))

    override fun getDocumentList(): Observable<List<VKDocument>> = createObservable(VKGetDocumentListRequest())

    override fun deleteDocument(id: Int, ownerId: Int): Observable<Boolean> = createObservable(VKDeleteDocumentRequest(id, ownerId))

    override fun editDocument(id: Int, ownerId: Int, title: String, tags: List<String>): Observable<Boolean> = createObservable(
        VKEditDocumentRequest(
            id, ownerId, title, tags
        )
    )

    override fun post(string: String, images: List<Uri>): Observable<Int> = createObservable(VKWallPostRequest(string, images, 141454621))

    private fun <T> createObservable(request: ApiCommand<T>): Observable<T> = Observable.create {
        VK.execute(request, VKApiEmittedCallback<T>(it))
    }
}