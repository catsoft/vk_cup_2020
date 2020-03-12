package com.c.v.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.c.v.BuildConfig
import java.io.File

object ViewLocalFilesUtil {
    fun openFile(context: Context, fileImagePath: File) {
        val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", fileImagePath)
        val url = uri.toString()
        val intent = Intent(Intent.ACTION_VIEW)
        when {
            url.contains(".doc", true) || url.contains(".docx", true) -> { // Word document
                intent.setDataAndType(uri, "application/msword")
            }
            url.contains(".pdf", true) -> { // PDF file
                intent.setDataAndType(uri, "application/pdf")
            }
            url.contains(".ppt", true) || url.contains(".pptx", true) -> { // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
            }
            url.contains(".xls", true) || url.contains(".xlsx", true) -> { // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel")
            }
            url.contains(".zip", true) || url.contains(".rar", true) -> { // WAV audio file
                intent.setDataAndType(uri, "application/x-wav")
            }
            url.contains(".rtf", true) -> { // RTF file
                intent.setDataAndType(uri, "application/rtf")
            }
            url.contains(".wav", true) || url.contains(".mp3", true) -> { // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav")
            }
            url.contains(".gif", true) -> { // GIF file
                intent.setDataAndType(uri, "image/gif")
            }
            url.contains(".jpg", true) || url.contains(".jpeg", true) || url.contains(".png", true) -> { // JPG file
                intent.setDataAndType(uri, "image/jpeg")
            }
            url.contains(".txt", true) -> { // Text file
                intent.setDataAndType(uri, "text/plain")
            }
            url.contains(".3gp", true) || url.contains(".mpg", true) || url.contains(".mpeg", true) || url.contains(".mpe", true) || url.contains(
                ".mp4", true
            ) || url.contains(".avi", true) -> { // Video files
                intent.setDataAndType(uri, "video/*")
            }
            else -> {
                intent.setDataAndType(uri, "*/*")
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }
}
