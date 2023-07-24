package com.soten.sjc.data.network.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

internal object RetrofitFactory {

    fun create(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(createOkhttpClient())
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(KotlinxConverterFactory.create())
            .build()
    }

    private fun createOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY),
            )
            .build()
    }
}
