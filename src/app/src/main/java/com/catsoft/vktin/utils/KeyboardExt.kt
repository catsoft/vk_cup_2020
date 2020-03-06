package com.catsoft.vktin.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardExt {
    fun hide(context: Context, view: View) {
        // Check if no view has focus:
        val focusView = view.findFocus()
        focusView?.let { v ->
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}