package com.catsoft.vktinG.ui.marketsList

import com.catsoft.vktinG.ui.base.BaseViewModel
import com.catsoft.vktinG.vkApi.model.VKCity
import com.catsoft.vktinG.vkApi.model.VKGroup
import com.vk.api.sdk.VKTokenExpiredHandler
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.*

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