package com.catsoft.vktinA.utils

class FileTypeUtil {
    companion object {
        fun isImage(ext: String) : Boolean {
            val lowerCaseExt = ext.toLowerCase()

            return lowerCaseExt in listOf("gif", "png", "jpg", "jpeg", "bmp", "webp")
        }
    }
}