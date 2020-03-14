package com.c.v.domain.userGroups

import com.c.v.domain.userGroups.dto.VKUserGroupDto
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface UserGroupsRepository {
    fun observeUserGroups() : Flowable<List<VKUserGroupDto>>

    fun getUserGroups() : Completable

    fun leaveGroups(ids: List<Int>): Completable

    fun observeUserGroupDetail(id : Int) : Single<VKUserGroupDto>
}