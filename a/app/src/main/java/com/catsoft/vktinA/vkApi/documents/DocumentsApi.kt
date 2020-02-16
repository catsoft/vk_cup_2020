package com.catsoft.vktinA.vkApi.documents

import com.catsoft.vktinA.vkApi.documents.model.VKApiDocument
import com.catsoft.vktinA.vkApi.documents.requests.VKDeleteDocumentRequest
import com.catsoft.vktinA.vkApi.documents.requests.VKDocumentListRequest
import com.vk.api.sdk.VK
import io.reactivex.Observable

class DocumentsApi : IDocumentsApi {

    override fun getList(): Observable<List<VKApiDocument>> = Observable.create<List<VKApiDocument>> {
        VK.execute(VKDocumentListRequest(), VKApiEmittedCallback<List<VKApiDocument>>(it))
    }

    override fun deleteDocument(id: Int, ownerId: Int): Observable<Boolean> = Observable.create<Boolean> {
        VK.execute(VKDeleteDocumentRequest(id, ownerId), VKApiEmittedCallback<Boolean>(it))
    }
}

