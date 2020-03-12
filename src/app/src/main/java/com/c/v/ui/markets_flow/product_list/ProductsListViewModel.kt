package com.c.v.ui.markets_flow.product_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.v.ui.base.BaseViewModel
import com.c.v.data.network.vkApi.model.VKProduct
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class ProductsListViewModel : BaseViewModel() {

    private val _loadPublisher = PublishSubject.create<Int>()

    private var _selectedGroup: Int = 0

    private val loader: Observable<List<VKProduct>> = _loadPublisher.flatMap { vkApi.getProductList(_selectedGroup) }

    private val _products = MutableLiveData<List<VKProduct>>()
    val products : LiveData<List<VKProduct>> = _products

    init {
        loader.compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)
    }

    private fun whenLoad(list: List<VKProduct>) {
        if (list.isNotEmpty()) {
            setSuccess()
            _products.postValue(list)
        } else {
            setIsEmpty()
        }
    }

    fun start(id: Int) {
        _selectedGroup = id

        _loadPublisher.onNext(1)
    }

    fun reload() {
        _loadPublisher.onNext(1)
    }
}
