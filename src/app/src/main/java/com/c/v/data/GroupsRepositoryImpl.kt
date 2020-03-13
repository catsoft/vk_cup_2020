package com.c.v.data

import com.c.v.data.db.VKGroupDao
import com.c.v.data.db.models.groups.VKGroupEntity
import com.c.v.data.dto.VKGroupDto
import com.c.v.data.network.vkApi.requests.VKGetGroupListRequest
import com.c.v.data.network.vkApi.requests.VKGroupLeaveRequest
import com.c.v.domain.markets.GroupsRepository
import com.google.android.play.core.internal.t
import com.vk.api.sdk.VK
import io.reactivex.*
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.modelmapper.ModelMapper

class GroupsRepositoryImpl(
    private val groupDao: VKGroupDao,
    private val modelMapper: ModelMapper,
    private val scheduler: Scheduler = Schedulers.io()
) : GroupsRepository {

    override fun observeGroups(): Flowable<List<VKGroupDto>> {
        return groupDao.getAll().map { it.map { vkGroup -> modelMapper.map(vkGroup, VKGroupDto::class.java) } }.subscribeOn(scheduler)
    }

    override fun getGroups(): Completable {
        return Single.fromCallable { VKGetGroupListRequest(0) }.map { request -> VK.executeSync(request) }.flatMapCompletable { response ->
            groupDao.insertAll(response.map { apiDocument -> modelMapper.map(apiDocument, VKGroupEntity::class.java) })
        }.subscribeOn(scheduler)
    }

    override fun leaveGroups(ids: List<Int>): Completable {
        return Single.fromCallable { ids.map { id -> VK.executeSync(VKGroupLeaveRequest(id)) } }
            .flatMapCompletable { groupDao.deleteAllById(it) }
            .subscribeOn(scheduler)
    }
}