package com.catsoft.vktinG.ui.marketsList

import androidx.lifecycle.ViewModel
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.vkApi.IVkApi
import com.catsoft.vktinG.vkApi.model.VKCity
import com.catsoft.vktinG.vkApi.model.VKGroup
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class MarketsListViewModel : ViewModel() {

    private val vkApi: IVkApi = SimpleDi.Instance.resolve(IVkApi::class.java)

    val compositeDisposable = CompositeDisposable()

    private var _emitter = PublishSubject.create<Int>()
    var cityPublisher = PublishSubject.create<VKCity?>()

    private val cityToGroups = _emitter.flatMap {
        vkApi.getMarketsList(0)
    }.map {
        it.filter { vkGroup -> vkGroup.market.enabled && vkGroup.deactivated.isEmpty() }.groupBy {
            it.city
        }
    }

    private val cities = cityToGroups.map {
        it.map { group ->
            group.key
        }
    }

    var citiesList = listOf<VKCity>()
    lateinit var selectedCity : VKCity

    val viewGroups = Observable.combineLatest(cityPublisher, cityToGroups, BiFunction<VKCity?, Map<VKCity, List<VKGroup>>, List<VKGroup>> { t1, t2 ->
        t2[t1] ?: error("Не по тому рельсу пошло")
    })

    init {
        cities.subscribe {
            if (it != null && it.isNotEmpty()) {
                selectCity(it.first())
                citiesList = it.toMutableList()
            }
        }.addTo(compositeDisposable)

        cityPublisher.subscribe {
            if (it != null) {
                selectedCity = it
            }
        }.addTo(compositeDisposable)
    }

    fun load() {
        _emitter.onNext(1)
    }

    fun selectCity(city: VKCity) {
        cityPublisher.onNext(city)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
