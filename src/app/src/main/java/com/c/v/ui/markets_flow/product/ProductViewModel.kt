package com.c.v.ui.markets_flow.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.v.ui.base.BaseViewModel
import com.c.v.data.network.vkApi.model.VKProduct
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ProductViewModel : BaseViewModel() {

    private val _productPublisher = PublishSubject.create<VKProduct>()
    private val _changeFavoritePublisher = PublishSubject.create<Boolean>()
    private val _isFavoritePublisher = PublishSubject.create<Boolean?>()

    private val _product = MutableLiveData<VKProduct>()
    val product: LiveData<VKProduct> = _product

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        _productPublisher.subscribe {
            _product.postValue(it)
        }.addTo(compositeDisposable)

        _productPublisher.flatMap { vkApi.getProduct(it.ownerId, it.id) }.compose(getTransformer(this::whenLoad)).subscribe()
            .addTo(compositeDisposable)

        _changeFavoritePublisher.subscribeBy(onError = { setErrorState(it) }) {
            val product = product.value!!
            (if (_isFavorite.value!!) vkApi.removeProductFromFavorite(product.ownerId, product.id)
            else vkApi.addProductToFavorite(product.ownerId, product.id)).observeOn(Schedulers.newThread()).subscribe {
                val newValue = !_isFavorite.value!!
                _isFavoritePublisher.onNext(newValue)
            }.addTo(compositeDisposable)
        }.addTo(compositeDisposable)

        _isFavoritePublisher.subscribe {
            _isFavorite.postValue(it)
        }.addTo(compositeDisposable)
    }

    private fun whenLoad(product: VKProduct) {
        _isFavoritePublisher.onNext(product.isFavorite)
        setSuccessState()
    }

    fun start(product: VKProduct) {
        _productPublisher.onNext(product)
    }

    fun toggleIsFavorite() {
        _changeFavoritePublisher.onNext(true)
    }
}
