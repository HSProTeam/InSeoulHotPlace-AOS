package com.soten.sjc.ui

import android.view.View
import androidx.databinding.BindingAdapter
import com.soten.sjc.R
import com.soten.sjc.domain.model.congestion.CongestionInfo

@BindingAdapter("congest")
fun View.setCongestImage(congestionInfo: CongestionInfo) {
    setBackgroundResource(when (congestionInfo.areaCongestNumber) {
        1 -> R.drawable.ic_congest_blue
        2 -> R.drawable.ic_congest_green
        3 -> R.drawable.ic_congest_yellow
        4 -> R.drawable.ic_congest_red
        else -> R.drawable.ic_congest_blue
    })
}