package com.catsoft.vktin.vkApi.documents

import com.catsoft.vktin.vkApi.documents.model.VKApiDocument
import io.reactivex.Observable

interface IDocumentsApi {
    fun getList(): Observable<List<VKApiDocument>>

    fun deleteDocument(id: Int, ownerId: Int): Observable<Boolean>

    fun editDocument(id: Int, ownerId: Int, title: String, tags: List<String>): Observable<Boolean>
}