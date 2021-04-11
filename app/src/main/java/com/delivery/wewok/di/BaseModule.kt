package com.delivery.wewok.di

import com.delivery.wewok.base.DefaultErrorFactory
import com.delivery.wewok.base.ErrorFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseModule {
    @Binds
    internal abstract fun provideErrorFactory(defaultErrorFactory: DefaultErrorFactory): ErrorFactory
}