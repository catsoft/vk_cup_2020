package com.c.v.ui.check_in_flow.friends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.c.v.data.network.vkApi.model.VKUser
import com.c.v.domain.friends.FriendsRepository
import com.c.v.ui.base.BaseViewModel
import com.c.v.ui.check_in_flow.friends.dto.FriendsPresentationDto
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class FriendsListViewModel @Inject constructor(friendsRepository: FriendsRepository) : BaseViewModel() {

    private val _loader = friendsRepository.getFriends()

    private val _friends = MutableLiveData<List<VKUser>>()
    val friends: LiveData<List<VKUser>> = _friends


    val presentationFriends = Transformations.map(_friends) {
        it.map { FriendsPresentationDto.fromVKUser(it) }.toList()
    }

    init {
        setInProgressState()
        loadPlaces()
    }

    fun loadPlaces() {
        _loader.compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)
    }

    private fun whenLoad(list: List<VKUser>) {
        if (list.isEmpty()) {
            setEmptyState()
        } else {
            _friends.postValue(list.toMutableList())
            setSuccessState()
        }
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}