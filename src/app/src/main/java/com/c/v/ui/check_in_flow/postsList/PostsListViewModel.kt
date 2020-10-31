package com.c.v.ui.check_in_flow.postsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.c.v.data.network.vkApi.model.VKPost
import com.c.v.domain.wall.WallRepository
import com.c.v.ui.base.BaseViewModel
import com.c.v.ui.check_in_flow.postsList.dto.InitDto
import com.c.v.ui.check_in_flow.postsList.dto.PostPresentationDto
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PostsListViewModel @Inject constructor(wallRepository: WallRepository) : BaseViewModel() {

    private val _initDto = MutableLiveData<InitDto>()
    val initDto : LiveData<InitDto> = _initDto

    val loader = Transformations.map(_initDto) {
        wallRepository.getAll(it.userId).compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)
    }

    private val _posts = MutableLiveData<List<VKPost>>()
    val posts: LiveData<List<VKPost>> = _posts

    val presentationPosts = Transformations.map(_posts) {
        val places = it.map { PostPresentationDto.fromVKPost(it) }.toList()
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

    fun initArgs(initDto: InitDto) {
        _initDto.value = initDto
    }

    fun reload() {
        _initDto.value = _initDto.value
    }

    private fun whenLoad(list: List<VKPost>) {
        _posts.postValue(list.toMutableList())
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}