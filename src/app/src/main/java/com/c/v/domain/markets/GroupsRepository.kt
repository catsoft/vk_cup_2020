package com.c.v.domain.markets

import com.c.v.data.dto.VKGroupDto
import io.reactivex.Completable
import io.reactivex.Flowable

interface GroupsRepository {
    fun observeGroups() : Flowable<List<VKGroupDto>>

    fun getGroups() : Completable
}