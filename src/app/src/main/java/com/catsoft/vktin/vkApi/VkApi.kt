package com.catsoft.vktin.vkApi

import com.catsoft.vktin.vkApi.model.VKGroup
import com.catsoft.vktin.vkApi.model.VKPost
import com.catsoft.vktin.vkApi.requests.*
import com.vk.api.sdk.VK
import io.reactivex.Observable

class VkApi : IVkApi {

    override fun getGroupsList(id: Int): Observable<List<VKGroup>> = Observable.create<List<VKGroup>> {
        VK.execute(VKGetGroupListRequest(id), VKApiEmittedCallback<List<VKGroup>>(it))
    }

    override fun getLastPost(id: Int): Observable<VKPost?> = Observable.create {
        VK.execute(VKGetLastPostRequest(id), VKApiEmittedCallback(it))
    }

    override fun getCountFriendsInGroupPost(id: Int): Observable<Int> = Observable.create {
        VK.execute(VKGetFriendsRequest(id), VKApiEmittedCallback(it))
    }

    override fun groupLeave(id: Int): Observable<Int> = Observable.create {
        VK.execute(VKGroupLeaveRequest(id), VKApiEmittedCallback(it))
    }
}
