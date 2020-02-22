package com.catsoft.vktinG.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.vkApi.IVkApi
import com.catsoft.vktinG.ui.base.MutableStateData
import com.catsoft.vktinG.ui.base.StateData
import com.catsoft.vktinG.vkApi.model.VKProduct
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class ProductViewModel : ViewModel() {

    private val vkApi: IVkApi = SimpleDi.Instance.resolve(IVkApi::class.java)

    private val compositeDisposable = CompositeDisposable()

    private val _product = MutableStateData<VKProduct>()

    val product: StateData<VKProduct> = _product

    private val _isFavorite = MutableLiveData<Boolean?>()

    val isFavorite: LiveData<Boolean?> = _isFavorite

    init {
        _isFavorite.postValue(null)
    }

    fun setProduct(product: VKProduct) {
        _product.success(product)
        _isFavorite.postValue(product.isFavorite)

        vkApi.isLikedProduct(product.ownerId, product.id)
            .observeOn(Schedulers.newThread())
            .subscribe {
                _isFavorite.postValue(it)
            }
            .addTo(compositeDisposable)
    }

    fun toggleIsFavorite() {
        val product = _product.data.value!!
        (if (_isFavorite.value!!) vkApi.removeProductFromFavorite(product.ownerId, product.id)
        else vkApi.addProductToFavorite(product.ownerId, product.id))
            .observeOn(Schedulers.newThread())
            .subscribe {
            val newValue = !_isFavorite.value!!
            _isFavorite.postValue(newValue)
        }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
