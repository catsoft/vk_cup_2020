package com.catsoft.vktinA.vkApi.documents

import com.catsoft.vktinA.vkApi.documents.model.VKApiDocument
import io.reactivex.Observable

interface IDocumentsApi {
    fun getList(): Observable<List<VKApiDocument>>

    fun deleteDocument(id: Int, ownerId: Int): Observable<Boolean>
}