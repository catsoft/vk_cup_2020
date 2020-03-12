package com.c.v.ui.unsubscribe_flow.group_detail

import com.c.v.ui.base.BaseViewModel
import com.c.v.data.network.vkApi.model.VKPost
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class GroupDetailViewModel : BaseViewModel() {

    private val _loadPublisher = PublishSubject.create<Int>()

    val lastPost: Observable<VKPost?> = _loadPublisher.flatMap { vkApi.getLastPost(_groupId) }

    val countFriends: Observable<Int> = _loadPublisher.flatMap { vkApi.getCountFriendsInGroupPost(_groupId) }

    private var _groupId : Int = 0

    fun start(id : Int) {

        _groupId = id

        _loadPublisher.onNext(1)
    }
}
