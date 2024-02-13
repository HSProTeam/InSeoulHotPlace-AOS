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

internal inline fun <T, R> transformApiResult(
    apiResult: ApiResult<T>,
    successTransform: (T?) -> R
): Result<R> {
    return when (apiResult) {
        is ApiResult.Success -> Result.success(successTransform(apiResult.value))

        is ApiResult.Failure -> Result.failure(
            ApiException.Failure(
                apiResult.message.orEmpty(),
                apiResult.code ?: 0
            )
        )

        is ApiResult.NetworkError -> Result.failure(
            ApiException.NetworkError(apiResult.exception.message.orEmpty())
        )

        is ApiResult.Unexpected -> Result.failure(
            ApiException.Unexpected(apiResult.t?.message.orEmpty())
        )
    }
}

