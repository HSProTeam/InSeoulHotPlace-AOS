package com.soten.sjc.data.network

import kotlinx.serialization.Serializable
import okio.IOException

@Serializable
internal sealed class ApiResult<out T> {

    @Serializable
    data class Success<T>(val value: T?) : ApiResult<T>()

    @Serializable
    data class Failure(val code: Int?, val message: String?) : ApiResult<Nothing>()

    data class NetworkError(val exception: IOException) : ApiResult<Nothing>()

    data class Unexpected(val t: Throwable?) : ApiResult<Nothing>()
}
