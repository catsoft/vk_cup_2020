package com.c.v.ui.sharing_flow.share_content

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.v.domain.wall.WallRepository
import com.c.v.services.LoadFileRepository
import com.c.v.ui.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class ShareContentViewModel @Inject constructor(
    private val wallRepository: WallRepository,
    private val loadPhotoRepo : LoadFileRepository) : BaseViewModel() {

    private val _selectedImage = MutableLiveData<File>()

    val selectedImage: LiveData<File> = _selectedImage

    private val _isLoadEnd = MutableLiveData<Unit>()

    val isLoadEnd: LiveData<Unit> = _isLoadEnd

    init {
        setSuccessState()
    }

    fun selectPhoto(selectedImage: Uri) {
        loadPhotoRepo.selectPhoto(selectedImage)
            .observeOn(Schedulers.newThread())
            .compose(getSingleTransformer({ _selectedImage.postValue(it) }))
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun sendPost(text: String) {
        val uri = _selectedImage.value!!.toUri()
        setInProgressState()
        wallRepository.post(text, listOf(uri))
            .compose(getCompletableTransformer({ _isLoadEnd.postValue(Unit) }, this::setLocalError))
            .subscribe()
            .addTo(compositeDisposable)
    }

    private fun setLocalError(t: Throwable) {
        setSuccessState()
        setError(t)
    }
}