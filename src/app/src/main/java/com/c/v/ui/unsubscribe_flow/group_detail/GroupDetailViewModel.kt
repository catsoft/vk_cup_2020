package com.c.v.ui.unsubscribe_flow.group_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.v.domain.userGroups.UserGroupsRepository
import com.c.v.mapper.user_group.UserGroupDtoToDetailPresentationMapper
import com.c.v.ui.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class GroupDetailViewModel @Inject constructor(
    private val groupsRepository: UserGroupsRepository,
    private val groupMapper: UserGroupDtoToDetailPresentationMapper
)  : BaseViewModel() {
    private val _groupItem = MutableLiveData<VKUserGroupDetailPresentation>()
    val groupItem: LiveData<VKUserGroupDetailPresentation> = _groupItem

    fun start(id: Int) {
        groupsRepository.observeUserGroupDetail(id)
            .map { groupMapper.map(it) }
            .compose(getFlowableTransformer(this::whenLoad))
            .subscribe()
            .addTo(compositeDisposable)

        groupsRepository.getUserGroupDetail(id)
            .subscribe()
            .addTo(compositeDisposable)
    }

    private fun whenLoad(group: VKUserGroupDetailPresentation) {
        _groupItem.postValue(group)
    }
}