package com.catsoft.vktin.ui.markets_flow.city_selecting

import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKCity
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class CitiesListViewModel : BaseViewModel() {

    private val _loadPublisher = PublishSubject.create<Int>()

    val cities: Observable<List<VKCity>> = _loadPublisher.flatMap { vkApi.getCitiesList() }

    override fun initInner() {
        cities.subscribeBy({ setOnError(it) }) {
            if (it.isNotEmpty()) {
                setSuccess()
            } else {
                setIsEmpty()
            }
        }.addTo(compositeDisposable)
    }

    override fun start() {
        super.start()

        _loadPublisher.onNext(1)
    }
}
