package com.c.v.ui.unsubscribe_flow.group_list

import androidx.lifecycle.Transformations
import com.c.v.domain.markets.GroupsRepository
import com.c.v.ui.base.ListBaseViewModel
import com.c.v.utils.repost
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import org.modelmapper.ModelMapper
import javax.inject.Inject

class GroupListViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository,
    private val mapper : ModelMapper
) : ListBaseViewModel<VKGroupPresentation>() {

    private val groupsObserver = groupsRepository.observeGroups()
    private val groupLoader = groupsObserver
        .map { it.map { group -> mapper.map(group, VKGroupPresentation::class.java) } }

    val isAnySelected = Transformations.map(list) { it.any { group -> group.isSelected } }

    init {
        groupLoader.compose(getFlowableTransformer(this::whenLoadList))
            .subscribe().addTo(compositeDisposable)

        load()
    }

    fun load() {
        groupsRepository.getGroups().subscribeBy(onError = {}).addTo(compositeDisposable)
    }

    fun toggle(item: VKGroupPresentation) {
        item.isSelected = !item.isSelected
        mutableList.repost()
    }

    fun unsubscribe() {
        val leavesGroups = list.value!!.filter { it.isSelected }.map { it.id }.toList()
        groupsRepository.leaveGroups(leavesGroups).subscribe().addTo(compositeDisposable)
    }
}