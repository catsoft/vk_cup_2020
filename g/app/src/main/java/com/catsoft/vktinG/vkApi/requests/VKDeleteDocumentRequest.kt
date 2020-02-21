package com.catsoft.vktinG.vkApi.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKDeleteDocumentRequest(docId : Int, ownerId : Int) : VKRequest<Boolean>("docs.delete") {

    init {
        addParam("doc_id", docId)
        addParam("owner_id", ownerId)
    }

    override fun parse(r: JSONObject): Boolean {
        return true
    }
}

