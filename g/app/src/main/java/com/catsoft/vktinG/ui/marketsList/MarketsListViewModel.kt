package com.catsoft.vktinG.ui.marketsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.vkApi.IVkApi
import com.catsoft.vktinG.ui.base.MutableStateData
import com.catsoft.vktinG.ui.base.StateData
import com.catsoft.vktinG.vkApi.model.VKCity
import com.catsoft.vktinG.vkApi.model.VKGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class MarketsListViewModel : ViewModel() {

    private val vkApi: IVkApi = SimpleDi.Instance.resolve(IVkApi::class.java)

    private val compositeDisposable = CompositeDisposable()

    private val _groups = MutableStateData<List<VKGroup>>()

    private var _groupList = mutableListOf<VKGroup>()

    val groups: StateData<List<VKGroup>> = _groups


    private val _cities = MutableLiveData<List<VKCity>>()

    val cities : LiveData<List<VKCity>> = _cities


    fun loadMarkets() {
        _groups.loading()
        vkApi.getMarketsList(0)
            .map { it.filter { vkGroup -> vkGroup.market.enabled && vkGroup.deactivated.isEmpty() } }
            .subscribeBy({
            _groups.error(it)
        }) {
            _groupList = it.toMutableList()
            _groups.success(_groupList)
            _cities.postValue(_groupList.map { vkGroup -> vkGroup.city }.distinctBy { city -> city.id })
        }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
