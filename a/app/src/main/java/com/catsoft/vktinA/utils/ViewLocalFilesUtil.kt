package com.catsoft.vktinA.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class ViewLocalFilesUtil {
    companion object {
        fun openFile(url: String, context : Context, uri: Uri) {
            val intent = Intent(Intent.ACTION_VIEW)
            when {
                url.contains(".doc") || url.contains(".docx") -> { // Word document
                    intent.setDataAndType(uri, "application/msword")
                }
                url.contains(".pdf") -> { // PDF file
                    intent.setDataAndType(uri, "application/pdf")
                }
                url.contains(".ppt") || url.contains(".pptx") -> { // Powerpoint file
                    intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
                }
                url.contains(".xls") || url.contains(".xlsx") -> { // Excel file
                    intent.setDataAndType(uri, "application/vnd.ms-excel")
                }
                url.contains(".zip") || url.contains(".rar") -> { // WAV audio file
                    intent.setDataAndType(uri, "application/x-wav")
                }
                url.contains(".rtf") -> { // RTF file
                    intent.setDataAndType(uri, "application/rtf")
                }
                url.contains(".wav") || url.contains(".mp3") -> { // WAV audio file
                    intent.setDataAndType(uri, "audio/x-wav")
                }
                url.contains(".gif") -> { // GIF file
                    intent.setDataAndType(uri, "image/gif")
                }
                url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png") -> { // JPG file
                    intent.setDataAndType(uri, "image/jpeg")
                }
                url.contains(".txt") -> { // Text file
                    intent.setDataAndType(uri, "text/plain")
                }
                url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(
                    ".mp4"
                ) || url.contains(".avi") -> { // Video files
                    intent.setDataAndType(uri, "video/*")
                }
                else -> {
                    intent.setDataAndType(uri, "*/*")
                }
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}