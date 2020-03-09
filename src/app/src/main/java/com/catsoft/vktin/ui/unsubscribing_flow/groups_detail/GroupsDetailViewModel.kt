package com.catsoft.vktin.ui.unsubscribing_flow.groups_detail

import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKPost
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class GroupsDetailViewModel : BaseViewModel() {

    private val _loadPublisher = PublishSubject.create<Int>()

    val lastPost: Observable<VKPost?> = _loadPublisher.flatMap { vkApi.getLastPost(_groupId) }

    val countFriends: Observable<Int> = _loadPublisher.flatMap { vkApi.getCountFriendsInGroupPost(_groupId) }

    private var _groupId : Int = 0

    override fun initInner() {

    }

    fun start(id : Int) {
        start()

        _groupId = id

        _loadPublisher.onNext(1)
    }
}
