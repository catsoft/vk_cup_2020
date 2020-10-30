package com.c.v.ui.check_in_flow.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.c.v.data.network.vkApi.model.VKPlace
import com.c.v.ui.base.BaseViewModel
import com.c.v.data.network.vkApi.model.VKPost
import com.c.v.domain.wall.WallRepository
import io.reactivex.rxkotlin.addTo
import java.util.*
import javax.inject.Inject

class PlacesListViewModel @Inject constructor(private val wallRepository: WallRepository) : BaseViewModel() {

    private val _loader = wallRepository.getAll()

    private val _posts = MutableLiveData<List<VKPost>>()
    val posts: LiveData<List<VKPost>> = _posts

    val places: LiveData<List<VKPlace>> = Transformations.map(_posts) {
        listOf(VKPlace(Calendar.getInstance(), 0))
    }

    init {
        setInProgressState()
        loadDocs()
    }

    fun loadDocs() {
        _loader.compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)
    }

    private fun whenLoad(list: List<VKPost>) {
        if (list.isEmpty()) {
            setEmptyState()
        } else {
            _posts.postValue(list.toMutableList())
            setSuccessState()
        }
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}