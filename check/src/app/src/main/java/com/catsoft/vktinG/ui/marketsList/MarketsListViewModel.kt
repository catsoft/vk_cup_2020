package com.catsoft.vktinG.ui.marketsList

import com.catsoft.vktinG.ui.base.BaseViewModel
import com.catsoft.vktinG.vkApi.model.VKCity
import com.catsoft.vktinG.vkApi.model.VKGroup
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class MarketsListViewModel : BaseViewModel() {

    private var _loadPublisher = PublishSubject.create<Int>()
    private var _cityPublisher = PublishSubject.create<VKCity?>()

    private val cityToGroups = _loadPublisher.flatMap { vkApi.getMarketsList(0) }.map {
        it.filter { vkGroup -> vkGroup.market.enabled && vkGroup.deactivated.isEmpty() }.groupBy { group -> group.city } }

    private val cities = cityToGroups.map { it.map { group -> group.key } }

    var citiesList = listOf<VKCity>()
    var selectedCity: VKCity? = null

    val selectedCityObserver: Observable<VKCity?> = _cityPublisher

    val viewGroups = Observable.combineLatest(_cityPublisher, cityToGroups, BiFunction<VKCity?, Map<VKCity, List<VKGroup>>, List<VKGroup>> { t1, t2 ->
        t2[t1] ?: error("Не по тому рельсу пошло")
    })

    override fun initInner() {

        cities.subscribe {
            if (it != null && it.isNotEmpty()) {
                if (selectedCity == null) {
                    selectCity(it.first())
                } else {
                    _cityPublisher.onNext(selectedCity!!)
                }
                citiesList = it.toMutableList()
            }
        }.addTo(compositeDisposable)

        selectedCityObserver.subscribe {
            if (it != null) {
                selectedCity = it
            }
        }.addTo(compositeDisposable)

        viewGroups.subscribeBy({ setOnError(it) }) {
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

        _loadPublisher.onNext(1)
    }

    fun selectCity(city: VKCity) {
        _cityPublisher.onNext(city)
    }
}