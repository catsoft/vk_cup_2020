package com.c.v.ui.unsubscribe_flow.user_group_list

import androidx.lifecycle.Transformations
import com.c.v.domain.userGroups.UserGroupsRepository
import com.c.v.mapper.user_group.UserGroupDtoToItemPresentationMapper
import com.c.v.ui.base.ListBaseViewModel
import com.c.v.utils.repost
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class UserGroupListViewModel @Inject constructor(
    private val groupsRepository: UserGroupsRepository,
    private val groupMapper : UserGroupDtoToItemPresentationMapper
) : ListBaseViewModel<VKUserGroupItemPresentation>() {

    private val groupsObserver = groupsRepository.observeUserGroups()
    private val groupLoader = groupsObserver
        .map { it.map { group -> groupMapper.map(group) } }

    val isAnySelected = Transformations.map(list) { it.any { group -> group.isSelected } }

    init {
        groupLoader.compose(getFlowableTransformer(this::whenLoadList))
            .subscribe().addTo(compositeDisposable)

        load()
    }

    fun load() {
        groupsRepository.getUserGroups().subscribeBy(onError = {}).addTo(compositeDisposable)
    }

    fun toggle(item: VKUserGroupItemPresentation) {
        item.isSelected = !item.isSelected
        mutableList.repost()
    }

    fun unsubscribe() {
        val leavesGroups = list.value!!.filter { it.isSelected }.map { it.id }.toList()
        groupsRepository.leaveGroups(leavesGroups).subscribe().addTo(compositeDisposable)
    }
}