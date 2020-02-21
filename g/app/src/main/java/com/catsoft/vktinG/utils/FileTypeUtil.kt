package com.catsoft.vktinG.utils

import java.util.*

object FileTypeUtil {
    fun isImage(ext: String) : Boolean {
        val lowerCaseExt = ext.toLowerCase(Locale.getDefault())

        return lowerCaseExt in listOf("gif", "png", "jpg", "jpeg", "bmp", "webp")
    }
}
