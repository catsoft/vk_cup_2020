package com.c.v.mapper.user_group

import com.c.v.domain.userGroups.dto.VKUserGroupDto
import com.c.v.mapper.IMapper
import com.c.v.ui.unsubscribe_flow.user_group_detail.VKUserGroupDetailPresentation

class UserGroupDtoToDetailPresentationMapper : IMapper<VKUserGroupDto, VKUserGroupDetailPresentation> {
    override fun map(from: VKUserGroupDto): VKUserGroupDetailPresentation {
        return VKUserGroupDetailPresentation(
            id = from.id,
            name = from.name,
            screenName = from.screenName,
            members_count = from.members_count,
            description = from.description,
            friendsCount = from.friendsCount,
            last_post_date = from.last_post_date,
            shareUrl = "http://vk.com/club${from.id}"
        )
    }
}