package com.c.v.domain.wall

import android.net.Uri
import com.c.v.data.network.vkApi.model.VKPost
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface WallRepository {
    fun post(string: String, images: List<Uri>) : Completable

    fun getAll(ownerId: Int = 0) : Observable<List<VKPost>>
}