package com.catsoft.vktin.ui.unsubscribe_flow.group_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKGroup
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class GroupListViewModel : BaseViewModel() {

    private var subscriptionList = mutableListOf<Int>()

    private var _subscriptionPublisher = PublishSubject.create<List<Int>>()

    val subscription: Observable<List<Int>> = _subscriptionPublisher


    private val loader = vkApi.getGroupsList(0)

    private val _groups = MutableLiveData<List<VKGroup>>()
    val groups: LiveData<List<VKGroup>> = _groups


    init {
        setIsProgress()
        load()
    }

    fun load() {
        loader.compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)
    }

    private fun whenLoad(list: List<VKGroup>) {
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