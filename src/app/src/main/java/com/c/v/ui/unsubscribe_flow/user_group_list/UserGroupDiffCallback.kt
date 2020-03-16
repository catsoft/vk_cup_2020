package com.c.v.ui.unsubscribe_flow.user_group_list

import com.c.v.ui.WithIdDiffCallback

class UserGroupDiffCallback(newItems: List<VKUserGroupItemPresentation>, oldItems: List<VKUserGroupItemPresentation>)
    : WithIdDiffCallback<VKUserGroupItemPresentation>(newItems, oldItems) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        newItem.isSelected = oldItem.isSelected

        return oldItemPosition == newItemPosition
    }
}