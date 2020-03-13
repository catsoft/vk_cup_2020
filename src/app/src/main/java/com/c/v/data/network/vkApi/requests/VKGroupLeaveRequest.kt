package com.c.v.data.network.vkApi.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGroupLeaveRequest(private val groupId : Int): VKRequest<Int>("groups.leave") {

    init {
        addParam("group_id", groupId)
    }

    override fun parse(r: JSONObject): Int {

        return groupId
    }
}


