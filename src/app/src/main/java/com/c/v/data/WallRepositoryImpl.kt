package com.c.v.data

import android.net.Uri
import com.c.v.data.network.vkApi.model.VKPost
import com.c.v.data.network.vkApi.requests.VKGetAllPostsInWallRequest
import com.c.v.data.network.vkApi.requests.VKWallPostRequest
import com.c.v.domain.wall.WallRepository
import com.vk.api.sdk.VK
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class WallRepositoryImpl(private val scheduler: Scheduler = Schedulers.io()) : WallRepository {
    override fun post(string: String, images: List<Uri>) =
        Completable.fromCallable { VK.executeSync(VKWallPostRequest(string, images)) }.subscribeOn(scheduler)

    override fun getAll(ownerId: Int): Observable<List<VKPost>> =
        Observable.fromCallable { VK.executeSync(VKGetAllPostsInWallRequest(ownerId)) }.subscribeOn(scheduler)
}