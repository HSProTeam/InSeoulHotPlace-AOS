package com.soten.sjc.data.repository

import com.soten.sjc.data.db.dao.BookmarkDao
import com.soten.sjc.data.db.entity.area.BookmarkEntity
import com.soten.sjc.data.mapper.toDomain
import com.soten.sjc.data.network.api.OpenApi
import com.soten.sjc.data.network.transformApiResult
import com.soten.sjc.domain.model.congestion.CongestionInfos
import com.soten.sjc.domain.repository.CongestRepository
import javax.inject.Inject

internal class CongestRepositoryImpl @Inject constructor(
    private val openApi: OpenApi,
    private val bookmarkDao: BookmarkDao
) : CongestRepository {

    override suspend fun fetchCongests(): Result<CongestionInfos> {
        return transformApiResult(openApi.fetchRealTimeCongest()) { congestDtos ->
            congestDtos?.congests ?: return@transformApiResult CongestionInfos.EMPTY

            val bookmarks = bookmarkDao.fetchAllBookmark().map { bookmark ->
                bookmark.areaName
            }

            CongestionInfos(
                congestDtos.congests.map { congestDto ->
                    if (bookmarks.contains(congestDto.areaName)) {
                        congestDto.toDomain(true)
                    } else {
                        congestDto.toDomain()
                    }
                }
            )
        }
    }

    override suspend fun insertBookmark(areaName: String) {
        bookmarkDao.insertBookmark(BookmarkEntity(areaName))
    }

    override suspend fun deleteBookmark(areaName: String) {
        bookmarkDao.deleteBookmark(BookmarkEntity(areaName))
    }
}
