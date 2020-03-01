package com.catsoft.vktinF.vkApi.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKGroupLeaveRequest(groupId : Int): VKRequest<Int>("groups.leave") {

    init {
        addParam("group_id", groupId)
    }

    override fun parse(r: JSONObject): Int {

        return 1
    }
}


