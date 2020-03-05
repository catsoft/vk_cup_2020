package com.catsoft.vktin.ui.groupsList

import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKGroup
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class GroupsListViewModel : BaseViewModel() {

    private var _loadPublisher = PublishSubject.create<Int>()

    private var subscibtionList = mutableListOf<Int>()

    private var _subscribtionPublisher = PublishSubject.create<List<Int>>()

    private var _unsubscribePublisher = PublishSubject.create<Int>()

    val groups: Observable<List<VKGroup>> = _loadPublisher.flatMap { vkApi.getGroupsList(0) }.map { it.reversed() }

    val subscribtion: Observable<List<Int>> = _subscribtionPublisher

    val unsubscribeObservable: Observable<Int> = _unsubscribePublisher

    override fun initInner() {
        groups.subscribeBy(onError = { setOnError(it) }) {
            if (it.isEmpty()) {
                setIsEmpty()
            } else {
                setSuccess()
            }
        }.addTo(compositeDisposable)
    }

    override fun start() {
        super.start()

        _loadPublisher.onNext(1)
    }

    fun toggle(id: Int) {
        if (subscibtionList.contains(id)) {
            subscibtionList.remove(id)
        } else {
            subscibtionList.add(id)
        }
        _subscribtionPublisher.onNext(subscibtionList)
    }

    fun unsubscribe() {
        setIsProgress()

        Observable.zip(subscibtionList.map { vkApi.groupLeave(it) }, {i -> ""})
            .subscribe {
                _loadPublisher.onNext(1)
                subscibtionList.clear()
            }.addTo(compositeDisposable)
    }
}