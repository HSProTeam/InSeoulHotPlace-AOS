package com.soten.sjc.data.network.retrofit

import com.soten.sjc.BuildConfig
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
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY),
            )
        }

        return okHttpClientBuilder.build()
    }
}
