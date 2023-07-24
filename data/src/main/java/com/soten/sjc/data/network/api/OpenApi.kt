package com.soten.sjc.data.network.api

import com.soten.sjc.data.network.ApiResult
import com.soten.sjc.data.network.response.CongestDtos
import retrofit2.http.GET
import retrofit2.http.Query

internal interface OpenApi {

    @GET("SeoulRtd/getCategoryList")
    suspend fun fetchRealTimeCongest(
        @Query("sort") sort: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("category") category: String = "전체보기",
        @Query("count") count: Int = 500,
    ): ApiResult<CongestDtos>
}