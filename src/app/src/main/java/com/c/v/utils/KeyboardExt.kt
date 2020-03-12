package com.c.v.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardExt {
    fun hide(context: Context, view: View) {
        val focusView = view.findFocus()
        focusView?.let { v ->
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}