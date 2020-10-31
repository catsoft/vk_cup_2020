package com.c.v.domain.friends

import com.c.v.data.network.vkApi.IVkApi
import com.c.v.data.network.vkApi.model.VKUser
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class FriendsRepositoryImpl(
    val api: IVkApi,
    private val scheduler: Scheduler = Schedulers.io()
) : FriendsRepository {

    override fun getFriends(userId: Int): Observable<List<VKUser>> =
        api.getFriends(userId).subscribeOn(scheduler)
}