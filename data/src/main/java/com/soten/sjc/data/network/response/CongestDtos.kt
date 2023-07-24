package com.soten.sjc.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CongestDtos(
    @SerialName("row")
    val congests: List<CongestDto>
)
