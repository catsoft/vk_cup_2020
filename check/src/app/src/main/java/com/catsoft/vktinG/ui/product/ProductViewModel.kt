package com.catsoft.vktinG.ui.product

import com.catsoft.vktinG.ui.base.BaseViewModel
import com.catsoft.vktinG.vkApi.model.VKProduct
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
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
        _productPublisher.subscribe {
            currentProduct = it
            vkApi.isLikedProduct(it.ownerId, it.id).observeOn(Schedulers.newThread()).subscribe { isFavoriteResponse ->
                    _isFavoritePublisher.onNext(isFavoriteResponse)
                }.addTo(compositeDisposable)
        }.addTo(compositeDisposable)

        _changeFavoritePublisher.subscribeBy(onError = { setOnError(it) }) {
            val product = currentProduct
            (if (_isFavorite!!) vkApi.removeProductFromFavorite(product.ownerId, product.id)
            else vkApi.addProductToFavorite(product.ownerId, product.id)).observeOn(Schedulers.newThread()).subscribe {
                val newValue = !_isFavorite!!
                _isFavoritePublisher.onNext(newValue)
            }.addTo(compositeDisposable)
        }.addTo(compositeDisposable)

        _isFavoritePublisher.subscribe {
            _isFavorite = it
        }.addTo(compositeDisposable)
    }

    fun start(product: VKProduct) {

        _productPublisher.onNext(product)

        start()
    }

    fun toggleIsFavorite() {
        _changeFavoritePublisher.onNext(true)
    }
}
