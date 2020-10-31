package com.c.v.data

import com.c.v.data.db.user_groups.VKUserGroupDao
import com.c.v.data.network.vkApi.requests.VKGetCountFriendsInGroupRequest
import com.c.v.data.network.vkApi.requests.VKGetGroupListRequest
import com.c.v.data.network.vkApi.requests.VKGetPostsInWallRequest
import com.c.v.data.network.vkApi.requests.VKGroupLeaveRequest
import com.c.v.domain.userGroups.UserGroupsRepository
import com.c.v.domain.userGroups.dto.VKUserGroupDto
import com.c.v.mapper.user_group.UserGroupApiToEntityMapper
import com.c.v.mapper.user_group.UserGroupEntityToDtoMapper
import com.vk.api.sdk.VK
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserGroupsRepositoryImpl(
    private val vkUserGroupDao: VKUserGroupDao,
    private val userGroupApiToEntityMapper: UserGroupApiToEntityMapper,
    private val userGroupEntityToDtoMapper : UserGroupEntityToDtoMapper,
    private val scheduler: Scheduler = Schedulers.io()
) : UserGroupsRepository {

    override fun observeUserGroups(): Flowable<List<VKUserGroupDto>> {
        return vkUserGroupDao.getAll()
            .map { it.map(userGroupEntityToDtoMapper::map) }
            .subscribeOn(scheduler)
    }

    override fun getUserGroups(): Completable {
        return Single.fromCallable { VKGetGroupListRequest(0) }
            .map { VK.executeSync(it) }
            .map { it.map(userGroupApiToEntityMapper::map) }
            .flatMapCompletable(vkUserGroupDao::insertAll)
            .onErrorResumeNext { Completable.complete() }
            .subscribeOn(scheduler)
    }

    override fun leaveGroups(ids: List<Int>): Completable {
        return Single.fromCallable { ids.map { id -> VK.executeSync(VKGroupLeaveRequest(id)) } }
            .flatMapCompletable(vkUserGroupDao::deleteAllById)
            .subscribeOn(scheduler)
    }

    override fun observeUserGroupDetail(id: Int): Flowable<VKUserGroupDto> {
        return vkUserGroupDao.getById(id)
            .map(userGroupEntityToDtoMapper::map)
            .subscribeOn(scheduler)
    }

    override fun getUserGroupDetail(id: Int): Completable {
        return vkUserGroupDao.getById(id).map { group ->
                if (group.deactivated.isEmpty()) {
                    group.copy(
                        friendsCount = VK.executeSync(
                            VKGetCountFriendsInGroupRequest(
                                group.id
                            )
                        ),
                        last_post_date = VK.executeSync(VKGetPostsInWallRequest(group.id, 1)).firstOrNull()?.date
                    )
                } else group
            }.flatMapCompletable { vkUserGroupDao.update(it) }
            .onErrorResumeNext { Completable.complete() }
            .subscribeOn(scheduler)
    }
}