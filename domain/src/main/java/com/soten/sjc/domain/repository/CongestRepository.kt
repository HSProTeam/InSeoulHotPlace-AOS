package com.soten.sjc.domain.repository

import com.soten.sjc.domain.model.CongestionInfo

interface CongestRepository {

    suspend fun fetchCongests(): Result<List<CongestionInfo>>
}
