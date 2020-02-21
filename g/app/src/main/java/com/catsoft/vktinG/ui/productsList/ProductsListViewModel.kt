package com.catsoft.vktinG.ui.productsList

import androidx.lifecycle.ViewModel
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.vkApi.IVkApi
import com.catsoft.vktinG.ui.base.MutableStateData
import com.catsoft.vktinG.ui.base.StateData
import com.catsoft.vktinG.vkApi.model.VKProduct
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class ProductsListViewModel : ViewModel() {

    private val vkApi: IVkApi = SimpleDi.Instance.resolve(IVkApi::class.java)

    private val compositeDisposable = CompositeDisposable()

    private var groupId : Int = 0

    private val _groups = MutableStateData<List<VKProduct>>()

    private var _groupList = mutableListOf<VKProduct>()

    val groups: StateData<List<VKProduct>> = _groups

    fun setId(id : Int) {
        groupId = id
    }

    fun loadProducts() {
        _groups.loading()
        vkApi.getProductsList(groupId)
            .subscribeBy({
            _groups.error(it)
        }) {
            _groupList = it.toMutableList()
            _groups.success(_groupList)
        }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
