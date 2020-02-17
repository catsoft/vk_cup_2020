package com.catsoft.vktinA.utils

import java.util.*

class FileTypeUtil {
    companion object {
        fun isImage(ext: String) : Boolean {
            val lowerCaseExt = ext.toLowerCase(Locale.getDefault())

            return lowerCaseExt in listOf("gif", "png", "jpg", "jpeg", "bmp", "webp")
        }
    }
}