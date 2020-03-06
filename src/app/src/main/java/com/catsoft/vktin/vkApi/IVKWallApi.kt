package com.catsoft.vktin.vkApi

import android.net.Uri
import io.reactivex.Observable

interface IVKWallApi {
    fun post(string: String, images: List<Uri>): Observable<Int>
}