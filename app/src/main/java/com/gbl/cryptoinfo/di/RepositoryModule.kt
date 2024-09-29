package com.gbl.cryptoinfo.di

import com.gbl.cryptoinfo.data.RepositoryImpl
import com.gbl.cryptoinfo.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(repositoryImpl: RepositoryImpl): Repository = repositoryImpl
}