package com.soten.sjc.data.repository

import com.soten.sjc.data.mapper.toDomain
import com.soten.sjc.data.network.ApiResult
import com.soten.sjc.data.network.api.OpenApi
import com.soten.sjc.domain.exception.ApiException
import com.soten.sjc.domain.model.congestion.CongestionInfos
import com.soten.sjc.domain.repository.CongestRepository
import javax.inject.Inject

internal class CongestRepositoryImpl @Inject constructor(
    private val openApi: OpenApi,
    private val bookmarkDao: BookmarkDao
) : CongestRepository {

    override suspend fun fetchCongests(): Result<CongestionInfos> {
        return when (val apiResult = openApi.fetchRealTimeCongest()) {
            is ApiResult.Success -> {
                val bookmarks = bookmarkDao.fetchAllBookmark().map { bookmark ->
                    bookmark.areaName
                }

                Result.success(
                    CongestionInfos(
                        apiResult.value?.congests?.map { congestDto ->
                            if (bookmarks.contains(congestDto.areaName)) {
                                congestDto.toDomain(true)
                            } else {
                                congestDto.toDomain()
                            }
                        } ?: emptyList()
                    )
                )
            }

            is ApiResult.Failure -> Result.failure(
                ApiException.Failure(
                    apiResult.message.orEmpty(),
                    apiResult.code ?: 0
                )
            )

            is ApiResult.NetworkError -> Result.failure(ApiException.NetworkError(apiResult.exception.message.orEmpty()))
            is ApiResult.Unexpected -> Result.failure(ApiException.Unexpected(apiResult.t?.message.orEmpty()))
        }
    }

    override suspend fun insertBookmark(areaName: String) {
        bookmarkDao.insertBookmark(BookmarkEntity(areaName))
    }

    override suspend fun deleteBookmark(areaName: String) {
        bookmarkDao.deleteBookmark(BookmarkEntity(areaName))
    }
}
