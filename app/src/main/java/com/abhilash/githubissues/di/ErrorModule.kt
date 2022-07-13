package com.abhilash.githubissues.di

import com.abhilash.githubissues.data.error.ErrorManager
import com.abhilash.githubissues.data.error.ErrorMapper
import com.abhilash.githubissues.data.error.ErrorMapperSource
import com.abhilash.githubissues.data.error.ErrorUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// with @Module we Telling Dagger that, this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}
