package com.catsoft.vktin.ui.friends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiUserFull
import com.vk.sdk.api.model.VKUsersArray

class FriendsViewModel : ViewModel() {

    private val _friends = MutableLiveData<Array<VKApiUserFull>>()

    val friends : LiveData<Array<VKApiUserFull>> = _friends

    fun loadFriends() {
        var fields = mutableListOf("first_name", "photo_200")
        var parameters = mutableMapOf(
            Pair<String, Any>("fields", fields),
            Pair<String, Any>("order", "hints")
        )
        var vkPar = VKParameters(parameters)

        VKApi.friends().get(VKParameters(vkPar)).executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                var friends = (response?.parsedModel as VKUsersArray).toTypedArray()

                _friends.apply { value = friends }
            }
        })
    }
}