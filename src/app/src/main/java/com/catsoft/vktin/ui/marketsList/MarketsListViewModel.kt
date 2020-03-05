package com.catsoft.vktin.ui.marketsList

import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKCity
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class MarketsListViewModel : BaseViewModel() {

    private var _cityPublisher = PublishSubject.create<VKCity>()

    var selectedCity: VKCity? = null

    val groups = _cityPublisher.flatMap {
        vkApi.getMarketsList(it.id)
    }.map {
        it.filter { vkGroup -> vkGroup.deactivated.isEmpty() }
    }

    val selectedCityObservable: Observable<VKCity> = _cityPublisher

    override fun initInner() {

        _cityPublisher.subscribe {
            if (it != null) {
                selectedCity = it
            }
        }.addTo(compositeDisposable)

        groups.subscribeBy({ setOnError(it) }) {
            if (it != null) {
                if (it.isEmpty()) {
                    setIsEmpty()
                } else {
                    setSuccess()
                }
            }
        }.addTo(compositeDisposable)
    }

    override fun start() {
        super.start()

        _cityPublisher.onNext(VKCity(-1, ""))
    }

    fun selectCity(city: VKCity) {
        _cityPublisher.onNext(city)
    }
}