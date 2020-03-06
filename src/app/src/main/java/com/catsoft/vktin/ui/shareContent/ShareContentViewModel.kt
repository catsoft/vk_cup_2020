package com.catsoft.vktin.ui.shareContent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.vkApi.IVkApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ShareContentViewModel : ViewModel() {

    private val vkWallApi: IVkApi = SimpleDi.Instance.resolve(IVkApi::class.java)

    private val compositeDisposable = CompositeDisposable()

    private val _selectedImage = MutableLiveData<File>()

    val selectedImage: LiveData<File> = _selectedImage

    fun selectPhoto(selectedImage: Uri, context: Context) {

        Observable.create<File> {
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver?.query(selectedImage, filePathColumn, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                val imgDecodeString: String = cursor.getString(columnIndex)
                cursor.close()
                val image = BitmapFactory.decodeFile(imgDecodeString)!!

                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val imageFileName = "$timeStamp.img"
                val storageFile = File(context.cacheDir.absolutePath + "/" + imageFileName)
                FileOutputStream(storageFile).run {
                    image.compress(Bitmap.CompressFormat.JPEG, 70, this)
                    it.onNext(storageFile)
                }
            }
        }.observeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            _selectedImage.postValue(it)
        }.addTo(compositeDisposable)
    }

    fun sendPost(text: String) {
        val uri = _selectedImage.value!!.toUri()
        _selectedImage.postValue(null)
        vkWallApi.post(text, listOf(uri)).subscribe().addTo(compositeDisposable)
    }

    fun clear() {
        _selectedImage.value = null
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
