package com.catsoft.vktinF.ui.groupsDetail

import com.catsoft.vktinF.ui.base.BaseViewModel
import io.reactivex.subjects.PublishSubject

class GroupsDetailViewModel : BaseViewModel() {

    private val _loadPublisher = PublishSubject.create<Int>()

    val lastPost = _loadPublisher.flatMap { vkApi.getLastPost(_groupId) }

    val countFrieds = _loadPublisher.flatMap { vkApi.getCountFriendsInGroupPost(_groupId) }

    private var _groupId : Int = 0

    override fun initInner() {

    }

    fun start(id : Int) {
        start()

        _groupId = id

        _loadPublisher.onNext(1)
    }
}
