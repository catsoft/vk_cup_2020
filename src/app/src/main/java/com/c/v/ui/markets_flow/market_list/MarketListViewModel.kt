package com.c.v.ui.markets_flow.market_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.v.ui.base.BaseViewModel
import com.c.v.data.network.vkApi.IVkApi
import com.c.v.data.network.vkApi.model.VKCity
import com.c.v.data.network.vkApi.model.VKGroupApi
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MarketListViewModel @Inject constructor(val api : IVkApi) : BaseViewModel() {

    private var _cityPublisher = PublishSubject.create<VKCity>()

    private val groupsLoader: Observable<List<VKGroupApi>> = _cityPublisher.flatMap { vkApi.getMarketList(it.id) }


    private val _groups = MutableLiveData<List<VKGroupApi>>()
    val groups: LiveData<List<VKGroupApi>> = _groups

    private val _selectedCity = MutableLiveData<VKCity>()
    val selectedCity: LiveData<VKCity> = _selectedCity

    init {
        _cityPublisher.subscribe {
            _selectedCity.postValue(it)
        }.addTo(compositeDisposable)

        groupsLoader.compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)

        selectCity(VKCity(-1, ""))
    }

    private fun whenLoad(groups: List<VKGroupApi>) {
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

    fun reload() {
        _cityPublisher.onNext(_selectedCity.value!!)
    }
}