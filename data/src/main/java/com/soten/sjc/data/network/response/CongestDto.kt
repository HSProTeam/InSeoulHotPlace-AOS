package com.soten.sjc.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CongestDto(
    @SerialName("area_nm")
    val areaName: String,
    @SerialName("congestion_color")
    val congestionColor: String,
    @SerialName("area_congest_lvl")
    val areaCongestLevel: String,
    @SerialName("area_congest_num")
    val areaCongestNumber: Int,
    val category: String,
    val x: String,
    val y: String
)
