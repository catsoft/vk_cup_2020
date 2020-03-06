package com.catsoft.vktin.vkApi.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKEditDocumentRequest(docId : Int, ownerId : Int, title: String, tags: List<String>) : VKRequest<Boolean>("docs.edit") {

    init {
        addParam("doc_id", docId)
        addParam("owner_id", ownerId)
        addParam("title", title)
        addParam("tags", tags)
    }

    override fun parse(r: JSONObject): Boolean {
        return true
    }
}