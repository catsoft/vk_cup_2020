package com.catsoft.vktinG.vkApi

import com.catsoft.vktinG.vkApi.model.VKGroup
import com.catsoft.vktinG.vkApi.model.VKProduct
import io.reactivex.Observable

interface IVkApi {
    fun getMarketsList(id: Int): Observable<List<VKGroup>>

    fun getProductsList(idMarket: Int): Observable<List<VKProduct>>
}