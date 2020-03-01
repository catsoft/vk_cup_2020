package com.catsoft.vktinF.vkApi

import com.catsoft.vktinF.vkApi.model.VKGroup
import com.catsoft.vktinF.vkApi.model.VKPost
import com.catsoft.vktinF.vkApi.model.VKProduct
import io.reactivex.Observable

interface IVkApi {
    fun getGroupsList(id: Int): Observable<List<VKGroup>>

    fun getLastPost(id: Int) : Observable<VKPost?>

    fun getCountFriendsInGroupPost(id: Int) : Observable<Int>

    fun groupLeave(id: Int) : Observable<Int>
}