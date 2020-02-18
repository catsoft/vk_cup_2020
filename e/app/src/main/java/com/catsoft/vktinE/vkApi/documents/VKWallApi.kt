package com.catsoft.vktinE.vkApi.documents

import com.catsoft.vktinE.vkApi.documents.model.VKApiDocument
import com.catsoft.vktinE.vkApi.documents.requests.VKDeleteDocumentRequest
import com.catsoft.vktinE.vkApi.documents.requests.VKGetDocumentListRequest
import com.catsoft.vktinE.vkApi.documents.requests.VKEditDocumentRequest
import com.vk.api.sdk.VK
import io.reactivex.Observable

class VKWallApi : IVKWallApi {


//    override fun editDocument(id: Int, ownerId: Int, title: String, tags: List<String>): Observable<Boolean> = Observable.create<Boolean> {
//        VK.execute(VKEditDocumentRequest(id, ownerId, title, tags), VKApiEmittedCallback<Boolean>(it))
//    }
}

