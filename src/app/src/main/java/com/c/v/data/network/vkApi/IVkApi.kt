package com.c.v.data.network.vkApi

import com.c.v.data.network.vkApi.model.*
import io.reactivex.Observable

interface IVkApi {
    fun getMarketList(id: Int): Observable<List<VKGroupApi>>

    fun getProductList(idMarket: Int): Observable<List<VKProduct>>

    fun addProductToFavorite(ownerId: Int, idProduct: Int): Observable<Int>

    fun removeProductFromFavorite(ownerId: Int, idProduct: Int): Observable<Int>

    fun getProduct(ownerId: Int, idProduct: Int): Observable<VKProduct>

    fun getCityList() : Observable<List<VKCity>>

    fun getDocumentList(): Observable<List<VKDocument>>

    fun deleteDocument(id: Int, ownerId: Int): Observable<Boolean>

    fun getFriends(userId: Int) : Observable<List<VKUser>>

    fun editDocument(id: Int, ownerId: Int, title: String, tags: List<String>): Observable<Boolean>
}