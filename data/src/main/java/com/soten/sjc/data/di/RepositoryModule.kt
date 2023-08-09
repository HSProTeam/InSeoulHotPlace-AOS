package com.soten.sjc.data.di

import com.soten.sjc.data.repository.CongestRepositoryImpl
import com.soten.sjc.domain.repository.CongestRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Reusable
    abstract fun bindsCongestRepository(congestRepositoryImpl: CongestRepositoryImpl): CongestRepository
}
