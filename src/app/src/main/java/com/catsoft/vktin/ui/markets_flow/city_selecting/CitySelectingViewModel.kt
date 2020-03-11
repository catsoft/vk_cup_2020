package com.catsoft.vktin.ui.markets_flow.city_selecting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKCity
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo

class CitySelectingViewModel : BaseViewModel() {

    private val citiesLoader: Observable<List<VKCity>> = vkApi.getCityList()

    private val _cities = MutableLiveData<List<VKCity>>()
    val cities : LiveData<List<VKCity>> = _cities

    init {
        citiesLoader.compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)
    }

    private fun whenLoad(list : List<VKCity>) {
        if (list.isNotEmpty()) {
            setSuccess()
            _cities.postValue(list)
        } else {
            setIsEmpty()
        }
    }
}
