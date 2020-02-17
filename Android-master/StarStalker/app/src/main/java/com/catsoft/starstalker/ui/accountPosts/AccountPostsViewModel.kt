package com.catsoft.starstalker.ui.accountPosts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountPostsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Posts Fragment"
    }
    val text: LiveData<String> = _text
}