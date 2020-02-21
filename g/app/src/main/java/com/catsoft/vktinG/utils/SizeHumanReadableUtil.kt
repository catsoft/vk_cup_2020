package com.catsoft.vktinG.utils

import kotlin.math.abs

object SizeHumanReadableUtil {
    fun format(bytes: Long): String {
        val s = if (bytes < 0) "-" else ""
        var b = if (bytes == Long.MIN_VALUE) Long.MAX_VALUE else abs(bytes)

        return when {
            b < 1000L -> "$bytes B"
            b < 999950L -> String.format("%s%.1f КБ", s, b / 1e3)
            1000.let { b /= it; b } < 999950L -> String.format("%s%.1f МБ", s, b / 1e3)
            1000.let { b /= it; b } < 999950L -> String.format("%s%.1f ГБ", s, b / 1e3)
            1000.let { b /= it; b } < 999950L -> String.format("%s%.1f ТБ", s, b / 1e3)
            1000.let { b /= it; b } < 999950L -> String.format("%s%.1f ПБ", s, b / 1e3)
            else -> String.format("%s%.1f ЕБ", s, b / 1e6)
        }
    }
}
