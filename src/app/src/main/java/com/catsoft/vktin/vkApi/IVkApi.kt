package com.catsoft.vktin.vkApi

import com.catsoft.vktin.vkApi.model.VKApiDocument
import com.catsoft.vktin.vkApi.model.VKCity
import com.catsoft.vktin.vkApi.model.VKGroup
import com.catsoft.vktin.vkApi.model.VKPost
import com.catsoft.vktin.vkApi.model.VKProduct
import io.reactivex.Observable

interface IVkApi {
    fun getMarketsList(id: Int): Observable<List<VKGroup>>

    fun getProductsList(idMarket: Int): Observable<List<VKProduct>>

    fun addProductToFavorite(ownerId: Int, idProduct: Int): Observable<Int>

    fun removeProductFromFavorite(ownerId: Int, idProduct: Int): Observable<Int>

    fun getProduct(ownerId: Int, idProduct: Int): Observable<VKProduct>

    fun getCitiesList() : Observable<List<VKCity>>

    fun getGroupsList(id: Int): Observable<List<VKGroup>>

    fun getLastPost(id: Int) : Observable<VKPost?>

    fun getCountFriendsInGroupPost(id: Int) : Observable<Int>

    fun groupLeave(id: Int) : Observable<Int>

    fun getList(): Observable<List<VKApiDocument>>

    fun deleteDocument(id: Int, ownerId: Int): Observable<Boolean>

    fun editDocument(id: Int, ownerId: Int, title: String, tags: List<String>): Observable<Boolean>
}