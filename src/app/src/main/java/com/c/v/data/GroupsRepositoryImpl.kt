package com.c.v.data

import com.c.v.data.db.VKGroupDao
import com.c.v.data.db.models.groups.VKGroupEntity
import com.c.v.data.dto.VKGroupDto
import com.c.v.data.network.vkApi.requests.VKGetGroupListRequest
import com.c.v.domain.markets.GroupsRepository
import com.vk.api.sdk.VK
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.modelmapper.ModelMapper

class GroupsRepositoryImpl(
    private val groupDao: VKGroupDao,
    private val modelMapper: ModelMapper,
    private val scheduler: Scheduler = Schedulers.io()
) : GroupsRepository {

    override fun observeGroups(): Flowable<List<VKGroupDto>> {
        return groupDao.getGroups().map { it.map { vkGroup -> modelMapper.map(vkGroup, VKGroupDto::class.java) } }.subscribeOn(scheduler)
    }

    override fun getGroups(): Completable {
        return Single
            .fromCallable { VKGetGroupListRequest(0) }
            .map { request -> VK.executeSync(request) }
            .flatMapCompletable { response -> groupDao.replaceGroups(response.map { apiDocument -> modelMapper.map(apiDocument, VKGroupEntity::class.java) }) }
            .subscribeOn(scheduler)
    }
}