package com.c.v.ui.unsubscribe_flow.group_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.v.data.network.vkApi.IVkApi
import com.c.v.ui.base.BaseViewModel
import com.c.v.domain.markets.GroupsRepository
import com.c.v.ui.model.VKGroupPresentation
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import org.modelmapper.ModelMapper
import javax.inject.Inject

class GroupListViewModel @Inject constructor(
    private val api: IVkApi,
    private val groupsRepository: GroupsRepository,
    private val mapper : ModelMapper
) : BaseViewModel() {

    private var subscriptionList = mutableListOf<Int>()

    private var _subscriptionPublisher = PublishSubject.create<List<Int>>()

    val subscription: Observable<List<Int>> = _subscriptionPublisher


    private val loader = groupsRepository.observeGroups()

    private val _groups = MutableLiveData<List<VKGroupPresentation>>()
    val groups: LiveData<List<VKGroupPresentation>> = _groups


    init {
        setIsProgress()
        load()
    }

    fun load() {
        loader
            .map { it.map { group -> mapper.map(group, VKGroupPresentation::class.java) } }
            .compose(getFlowableTransformer(this::whenLoad))
            .subscribe().addTo(compositeDisposable)

        groupsRepository.getGroups().subscribe().addTo(compositeDisposable)
    }

    private fun whenLoad(list: List<VKGroupPresentation>) {
        if (list.isEmpty()) {
            setIsEmpty()
        } else {
            _groups.postValue(list)
            setSuccess()
        }
    }

    fun toggle(id: Int) {
        if (subscriptionList.contains(id)) {
            subscriptionList.remove(id)
        } else {
            subscriptionList.add(id)
        }
        _subscriptionPublisher.onNext(subscriptionList)
    }

    fun unsubscribe() {
        setIsProgress()

        Observable.zip(subscriptionList.map { vkApi.groupLeave(it) }) { "" }.subscribe {
                load()
                subscriptionList.clear()
            }.addTo(compositeDisposable)
    }
}