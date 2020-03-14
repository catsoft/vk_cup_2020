package com.c.v.mapper.user_group

import com.c.v.data.db.user_groups.VKUserGroupEntity
import com.c.v.data.network.vkApi.model.VKGroupApi
import com.c.v.mapper.IMapper

class UserGroupApiToEntityMapper :
    IMapper<VKGroupApi, VKUserGroupEntity> {
    override fun map(from: VKGroupApi): VKUserGroupEntity {
        return VKUserGroupEntity(
            id = from.id,
            name = from.name,
            screenName = from.screenName,
            deactivated = from.deactivated,
            isMember = from.isMember,
            isClosed = from.isClosed,
            photo50 = from.photo50,
            photo100 = from.photo100,
            photo200 = from.photo200,
            isHiddenFromFeed = from.isHiddenFromFeed,
            status = from.status,
            members_count = from.members_count,
            description = from.description,
            friendsCount = 0,
            last_post_date = 0
        )
    }
}