package com.c.v.data.network.vkApi

import android.net.Uri
import com.c.v.data.network.vkApi.model.VKDocument
import com.c.v.data.network.vkApi.model.VKCity
import com.c.v.data.network.vkApi.model.VKGroupApi
import com.c.v.data.network.vkApi.model.VKPost
import com.c.v.data.network.vkApi.model.VKProduct
import io.reactivex.Observable

interface IVkApi {
    fun getMarketList(id: Int): Observable<List<VKGroupApi>>

    fun getProductList(idMarket: Int): Observable<List<VKProduct>>

    fun addProductToFavorite(ownerId: Int, idProduct: Int): Observable<Int>

    fun removeProductFromFavorite(ownerId: Int, idProduct: Int): Observable<Int>

    fun getProduct(ownerId: Int, idProduct: Int): Observable<VKProduct>

    fun getCityList() : Observable<List<VKCity>>

    fun getGroupList(id: Int): Observable<List<VKGroupApi>>

    fun getLastPost(id: Int) : Observable<VKPost?>

    fun getCountFriendsInGroupPost(id: Int) : Observable<Int>

    fun groupLeave(id: Int) : Observable<Int>

    fun getDocumentList(): Observable<List<VKDocument>>

    fun deleteDocument(id: Int, ownerId: Int): Observable<Boolean>

    fun editDocument(id: Int, ownerId: Int, title: String, tags: List<String>): Observable<Boolean>

    fun post(string: String, images: List<Uri>): Observable<Int>
}