package com.c.v.domain.wall

import android.net.Uri
import io.reactivex.Completable

interface WallRepository {
    fun post(string: String, images: List<Uri>) : Completable
}