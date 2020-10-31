package com.c.v.domain.friends

import com.c.v.data.network.vkApi.model.VKUser
import io.reactivex.Observable

interface FriendsRepository {
    fun getFriends(userId: Int = 0) : Observable<List<VKUser>>
}