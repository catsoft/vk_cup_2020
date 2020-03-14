package com.c.v.data

import com.c.v.data.db.user_groups.VKUserGroupDao
import com.c.v.data.db.user_groups.VKUserGroupEntity
import com.c.v.domain.userGroups.dto.VKUserGroupDto
import com.c.v.data.network.vkApi.model.VKGroupApi
import com.c.v.data.network.vkApi.requests.VKGetCountFriendsInGroupRequest
import com.c.v.data.network.vkApi.requests.VKGetGroupListRequest
import com.c.v.data.network.vkApi.requests.VKGetLastPostRequest
import com.c.v.data.network.vkApi.requests.VKGroupLeaveRequest
import com.c.v.domain.userGroups.UserGroupsRepository
import com.c.v.mapper.user_group.UserGroupApiToEntityMapper
import com.c.v.mapper.user_group.UserGroupEntityToDtoMapper
import com.vk.api.sdk.VK
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

class UserGroupsRepositoryImpl(
    private val vkUserGroupDao: VKUserGroupDao,
    private val groupMapping: UserGroupApiToEntityMapper,
    private val groupMappingToEntity : UserGroupEntityToDtoMapper,
    private val scheduler: Scheduler = Schedulers.io()
) : UserGroupsRepository {

    override fun observeUserGroups(): Flowable<List<VKUserGroupDto>> {
        return vkUserGroupDao.getAll()
            .map { it.map { vkGroup -> groupMappingToEntity.map(vkGroup) } }.subscribeOn(scheduler)
    }

    override fun getUserGroups(): Completable {
        return Single.fromCallable { VKGetGroupListRequest(0) }
            .map { request -> VK.executeSync(request) }
            .map {
                it.map { vkGroupApi: VKGroupApi ->
                    groupMapping.map(vkGroupApi).also { group ->
                        if (group.deactivated.isEmpty()) {
                            group.copy(
                                friendsCount = VK.executeSync(
                                    VKGetCountFriendsInGroupRequest(
                                        group.id
                                    )
                                ),
                                last_post_date = VK.executeSync(VKGetLastPostRequest(group.id))?.date?.timeInMillis
                                    ?: 0
                            )
                        }
                    }
                }
            }
            .flatMapCompletable { groupsEntity -> vkUserGroupDao.insertAll(groupsEntity) }
            .subscribeOn(scheduler)
    }

    override fun leaveGroups(ids: List<Int>): Completable {
        return Single.fromCallable { ids.map { id -> VK.executeSync(VKGroupLeaveRequest(id)) } }
            .flatMapCompletable { vkUserGroupDao.deleteAllById(it) }
            .subscribeOn(scheduler)
    }

    override fun observeUserGroupDetail(id: Int): Single<VKUserGroupDto> {
        return vkUserGroupDao.getById(id)
            .map { groupMappingToEntity.map(it) }
            .subscribeOn(scheduler)
    }
}