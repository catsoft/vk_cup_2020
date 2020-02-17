package com.catsoft.vktinA.vkApi.documents

import com.catsoft.vktinA.vkApi.documents.model.VKApiDocument
import com.catsoft.vktinA.vkApi.documents.requests.VKDeleteDocumentRequest
import com.catsoft.vktinA.vkApi.documents.requests.VKGetDocumentListRequest
import com.catsoft.vktinA.vkApi.documents.requests.VKEditDocumentRequest
import com.vk.api.sdk.VK
import io.reactivex.Observable

class DocumentsApi : IDocumentsApi {

    override fun getList(): Observable<List<VKApiDocument>> = Observable.create<List<VKApiDocument>> {
        VK.execute(VKGetDocumentListRequest(), VKApiEmittedCallback<List<VKApiDocument>>(it))
    }

    override fun deleteDocument(id: Int, ownerId: Int): Observable<Boolean> = Observable.create<Boolean> {
        VK.execute(VKDeleteDocumentRequest(id, ownerId), VKApiEmittedCallback<Boolean>(it))
    }

    override fun editDocument(id: Int, ownerId: Int, title: String, tags: List<String>): Observable<Boolean> = Observable.create<Boolean> {
        VK.execute(VKEditDocumentRequest(id, ownerId, title, tags), VKApiEmittedCallback<Boolean>(it))
    }
}

