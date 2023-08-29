package com.soten.sjc.data.di

import com.soten.sjc.BuildConfig
import com.soten.sjc.data.network.api.OpenApi
import com.soten.sjc.data.network.retrofit.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesOpenApi(): OpenApi {
        return RetrofitFactory.create(BuildConfig.API_URL)
            .create(OpenApi::class.java)
    }
}
