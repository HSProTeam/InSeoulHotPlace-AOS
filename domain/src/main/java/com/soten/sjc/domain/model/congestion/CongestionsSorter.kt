package com.soten.sjc.domain.model.congestion

data class CongestionsSorter(
    val isAreaName: Boolean = true,
    val isLevel: Boolean = false,
    val isAscend: Boolean = true
)