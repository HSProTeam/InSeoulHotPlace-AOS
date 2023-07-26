package com.soten.sjc.domain.repository

import com.soten.sjc.domain.model.congestion.CongestionInfos

interface CongestRepository {

    suspend fun fetchCongests(): Result<CongestionInfos>
}
