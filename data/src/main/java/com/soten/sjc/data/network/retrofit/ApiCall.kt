package com.soten.sjc.data.network.retrofit

import com.soten.sjc.data.network.ApiResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

internal class ApiCall<T : ApiResult<*>>(
    private val call: Call<T>,
) : Call<T> {

    override fun clone(): Call<T> = ApiCall(call.clone())

    override fun execute(): Response<T> {
        throw UnsupportedOperationException("execute() 지원 X, enqueue를 사용해주세요.")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    override fun enqueue(callback: Callback<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val newResponse: Response<T> = Response.success(response.body())
                    callback.onResponse(
                        this@ApiCall,
                        newResponse,
                    )
                    return
                }

                val code = response.code()
                val message = response.message()

                val networkResponse = ApiResult.Failure(code, message) as T
                callback.onResponse(this@ApiCall, Response.success(networkResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResponse: T = when (t) {
                    is IOException -> ApiResult.NetworkError(t)
                    else -> ApiResult.Unexpected(t)
                } as T
                callback.onResponse(this@ApiCall, Response.success(networkResponse))
            }
        })
    }
}
