package com.soten.sjc.data.network.retrofit

import com.soten.sjc.data.network.ApiResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ApiResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "반환 타입은 반드시 ${ApiCall::class.java.name}<T> or ${ApiCall::class.java.name}<out T> 같은 형식"
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != ApiResult::class.java) {
            return null
        }

        check(responseType is ParameterizedType) { "응답 파라미터는 제네릭이어야합니다." }

        val successBodyType = getParameterUpperBound(0, responseType)

        return ApiResultCallAdapter<ApiResult<*>>(successBodyType)
    }
}
