package com.c.v.ui.check_in_flow.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.c.v.data.network.vkApi.model.VKPost
import com.c.v.domain.wall.WallRepository
import com.c.v.ui.base.BaseViewModel
import com.c.v.ui.check_in_flow.places.dto.PlacePresentationDto
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PlacesListViewModel @Inject constructor(wallRepository: WallRepository) : BaseViewModel() {


    private val _userId = MutableLiveData<Int>()
    val userId : LiveData<Int> = _userId

    val loader = Transformations.map(_userId) {
        wallRepository.getAll(it).compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)
    }

    private val _posts = MutableLiveData<List<VKPost>>()
    val posts: LiveData<List<VKPost>> = _posts

    val presentationPlaces = Transformations.map(_posts) {
        val postWithGeo = it.filter { it.geo?.place != null }
        val places = postWithGeo.map { PlacePresentationDto.fromVKGeo(it.geo!!) }.toList()
        if (places.isEmpty()) {
            setEmptyState()
        } else {
            setSuccessState()
        }
        places
    }

    init {
        setInProgressState()
    }

    fun initArgs(userId: Int) {
        _userId.value = userId
    }

    fun reload() {
        _userId.value = _userId.value
    }

    private fun whenLoad(list: List<VKPost>) {
        _posts.postValue(list.toMutableList())
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}