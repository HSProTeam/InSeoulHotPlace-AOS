package com.soten.sjc.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.setBackgroundTint(@ColorRes color: Int?) {
    this.backgroundTintList = if (color != null) {
        ContextCompat.getColorStateList(this.context, color)
    } else {
        null
    }
}
