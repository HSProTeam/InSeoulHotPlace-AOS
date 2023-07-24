package com.soten.sjc.data.mapper

import com.soten.sjc.data.network.response.CongestDto
import com.soten.sjc.domain.model.CongestionInfo

internal fun CongestDto.toDomain(): CongestionInfo {
    return CongestionInfo(areaName, areaCongestLevel, areaCongestNumber, category)
}
