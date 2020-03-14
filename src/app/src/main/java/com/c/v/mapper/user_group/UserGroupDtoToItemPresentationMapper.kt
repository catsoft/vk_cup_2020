package com.c.v.mapper.user_group

import com.c.v.domain.userGroups.dto.VKUserGroupDto
import com.c.v.mapper.IMapper
import com.c.v.ui.unsubscribe_flow.group_list.VKUserGroupItemPresentation

class UserGroupDtoToItemPresentationMapper :
    IMapper<VKUserGroupDto, VKUserGroupItemPresentation> {
    override fun map(from: VKUserGroupDto): VKUserGroupItemPresentation {
        return VKUserGroupItemPresentation(
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
            friendsCount = from.friendsCount,
            last_post_date = from.last_post_date
        )
    }
}