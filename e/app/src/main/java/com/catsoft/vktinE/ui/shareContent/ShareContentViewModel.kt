package com.catsoft.vktinE.ui.shareContent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catsoft.vktinE.vkApi.documents.IVKWallApi
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ShareContentViewModel(private val vkWallApi: IVKWallApi) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _selectedImage = MutableLiveData<Uri>()

    val selectedImage : LiveData<Uri?> = _selectedImage

    fun selectPhoto(selectedImage: Uri, context: Context) {
        Observable.create<Uri> {
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver?.query(selectedImage, filePathColumn, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                val imgDecodableString: String = cursor.getString(columnIndex)
                cursor.close()
                val image = BitmapFactory.decodeFile(imgDecodableString)!!

                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val imageFileName = "$timeStamp.img"
                val storageFile = File(context!!.cacheDir.absolutePath + "/" + imageFileName)
                FileOutputStream(storageFile).run {
                    image.compress(Bitmap.CompressFormat.JPEG, 70, this)
                    it.onNext(storageFile.toUri())
                }
            }
        }.subscribeBy {
            _selectedImage.value = selectedImage
        }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
