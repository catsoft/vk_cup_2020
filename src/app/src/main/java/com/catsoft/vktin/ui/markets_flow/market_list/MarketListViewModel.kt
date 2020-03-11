package com.catsoft.vktin.ui.markets_flow.market_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKCity
import com.catsoft.vktin.vkApi.model.VKGroup
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class MarketListViewModel : BaseViewModel() {

    private var _cityPublisher = PublishSubject.create<VKCity>()

    private val groupsLoader: Observable<List<VKGroup>> = _cityPublisher.flatMap { vkApi.getMarketsList(it.id) }


    private val _groups = MutableLiveData<List<VKGroup>>()
    val groups: LiveData<List<VKGroup>> = _groups

    private val _selectedCity = MutableLiveData<VKCity>()
    val selectedCity: LiveData<VKCity> = _selectedCity

    init {
        _cityPublisher.subscribe {
            _selectedCity.postValue(it)
        }.addTo(compositeDisposable)

        groupsLoader.compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)

        selectCity(VKCity(-1, ""))
    }

    private fun whenLoad(groups: List<VKGroup>) {
        if (groups.isEmpty()) {
            setIsEmpty()
        } else {
            setSuccess()
            _groups.postValue(groups)
        }
    }

    fun selectCity(selectedCity: VKCity) {
        _cityPublisher.onNext(selectedCity)
    }
}