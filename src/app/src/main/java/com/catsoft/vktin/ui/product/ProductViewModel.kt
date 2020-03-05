package com.catsoft.vktin.ui.product

import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.vkApi.model.VKProduct
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ProductViewModel : BaseViewModel() {

    private val _productPublisher = PublishSubject.create<VKProduct>()
    private val _changeFavoritePublisher = PublishSubject.create<Boolean>()
    private val _isFavoritePublisher = PublishSubject.create<Boolean?>()

    val product: Observable<VKProduct> = _productPublisher
    private lateinit var currentProduct: VKProduct
    private var _isFavorite: Boolean? = false

    val isFavorite: Observable<Boolean?> = _isFavoritePublisher

    override fun initInner() {

    }

    fun start(product: VKProduct) {

        _productPublisher.onNext(product)

        start()
    }

    fun toggleIsFavorite() {
        _changeFavoritePublisher.onNext(true)
    }
}
