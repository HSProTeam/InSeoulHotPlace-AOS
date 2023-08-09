package com.soten.sjc.data.network.retrofit

import com.soten.sjc.data.network.ApiResult
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Converter

internal class KotlinxConverter<T>(
    private val deserializer: KSerializer<T>,
    private val json: Json
) : Converter<ResponseBody, ApiResult<T>> {

    override fun convert(value: ResponseBody): ApiResult<T> {
        return value.use {
            ApiResult.Success(json.decodeFromString(deserializer, it.string()))
        }
    }
}
