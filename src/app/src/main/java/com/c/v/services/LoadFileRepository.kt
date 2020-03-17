package com.c.v.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class LoadFileRepository(
    private val context: Context,
    private val schedulers: Scheduler = Schedulers.io()) {

    fun selectPhoto(selectedImage: Uri): Single<File> {
        return Single.create<File> {
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
                    it.onSuccess(storageFile)
                }
            }
        }.subscribeOn(schedulers)
    }
}