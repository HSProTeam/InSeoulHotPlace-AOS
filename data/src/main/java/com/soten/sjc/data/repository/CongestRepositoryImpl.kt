package com.soten.sjc.data.repository

import com.soten.sjc.data.mapper.toDomain
import com.soten.sjc.data.network.ApiResult
import com.soten.sjc.data.network.api.OpenApi
import com.soten.sjc.domain.exception.ApiException
import com.soten.sjc.domain.model.CongestionInfo
import com.soten.sjc.domain.repository.CongestRepository
import javax.inject.Inject


internal class CongestRepositoryImpl @Inject constructor(
    private val openApi: OpenApi
) : CongestRepository {

    override suspend fun fetchCongests(): Result<List<CongestionInfo>> {
        return when (val apiResult = openApi.fetchRealTimeCongest()) {
            is ApiResult.Success -> Result.success(apiResult.value?.congests?.map {
                it.toDomain()
            } ?: emptyList())

            is ApiResult.Failure -> Result.failure(ApiException.Failure(apiResult.message.orEmpty(), apiResult.code ?: 0))
            is ApiResult.NetworkError -> Result.failure(ApiException.NetworkError(apiResult.exception.message.orEmpty()))
            is ApiResult.Unexpected -> Result.failure(ApiException.Unexpected(apiResult.t?.message.orEmpty()))
        }
    }
}