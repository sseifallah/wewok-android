package fr.mi.wewok.di

import fr.mi.wewok.base.DefaultErrorFactory
import fr.mi.wewok.base.ErrorFactory
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