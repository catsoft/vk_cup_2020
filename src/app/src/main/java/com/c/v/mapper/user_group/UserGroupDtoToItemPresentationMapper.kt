package com.c.v.mapper.user_group

import com.c.v.domain.userGroups.dto.VKUserGroupDto
import com.c.v.mapper.IMapper
import com.c.v.ui.unsubscribe_flow.user_group_list.VKUserGroupItemPresentation

class UserGroupDtoToItemPresentationMapper :
    IMapper<VKUserGroupDto, VKUserGroupItemPresentation> {
    override fun map(from: VKUserGroupDto): VKUserGroupItemPresentation {
        return VKUserGroupItemPresentation(
            id = from.id,
            name = from.name,
            screenName = from.screenName,
            deactivated = from.deactivated,
            photo200 = from.photo200
        )
    }
}