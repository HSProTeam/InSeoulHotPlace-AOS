package com.soten.sjc.domain.repository

import com.soten.sjc.domain.model.congestion.CongestionInfos

interface CongestRepository {

    suspend fun fetchCongests(): Result<CongestionInfos>

    suspend fun insertBookmark(areaName: String)

    suspend fun deleteBookmark(areaName: String)
}
