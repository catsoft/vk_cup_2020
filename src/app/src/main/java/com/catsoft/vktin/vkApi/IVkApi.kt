package com.catsoft.vktin.vkApi

import com.catsoft.vktin.vkApi.model.VKGroup
import com.catsoft.vktin.vkApi.model.VKPost
import io.reactivex.Observable

interface IVkApi {
    fun getGroupsList(id: Int): Observable<List<VKGroup>>

    fun getLastPost(id: Int) : Observable<VKPost?>

    fun getCountFriendsInGroupPost(id: Int) : Observable<Int>

    fun groupLeave(id: Int) : Observable<Int>
}