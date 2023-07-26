package com.soten.sjc.domain.model.congestion

data class CongestionInfo(
    val areaName: String,
    val areaCongestLevel: String,
    val areaCongestNumber: Int,
    val category: Category,
)
