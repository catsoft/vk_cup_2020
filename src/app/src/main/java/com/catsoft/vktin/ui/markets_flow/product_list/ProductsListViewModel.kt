package com.catsoft.vktin.ui.markets_flow.product_list

import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKProduct
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class ProductsListViewModel : BaseViewModel() {

    private val _loadPublisher = PublishSubject.create<Int>()

    private var _groupId: Int = 0

    val products: Observable<List<VKProduct>> = _loadPublisher.flatMap { vkApi.getProductsList(_groupId) }

    init {
        products.subscribeBy({ setOnError(it) }) {
            if (it.isNotEmpty()) {
                setSuccess()
            } else {
                setIsEmpty()
            }
        }.addTo(compositeDisposable)
    }

    fun start(id: Int) {
        _groupId = id

        _loadPublisher.onNext(1)
    }
}
