package com.catsoft.vktinG.ui.product

import androidx.lifecycle.ViewModel
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.vkApi.IVkApi
import com.catsoft.vktinG.ui.base.MutableStateData
import com.catsoft.vktinG.ui.base.StateData
import com.catsoft.vktinG.vkApi.model.VKProduct
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class ProductViewModel : ViewModel() {

    private val vkApi: IVkApi = SimpleDi.Instance.resolve(IVkApi::class.java)

    private val compositeDisposable = CompositeDisposable()

    private val _groups = MutableStateData<VKProduct>()

    val groups: StateData<VKProduct> = _groups

    fun setProduct(product : VKProduct) {
        _groups.success(product)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
