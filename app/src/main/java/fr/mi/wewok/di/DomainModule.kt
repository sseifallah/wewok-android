package fr.mi.wewok.di

import fr.mi.wewok.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    internal abstract fun provideWokRepository(WokRepository: WokRepositoryImpl): WokRepository

    @Binds
    internal abstract fun provideAuthRepository(AuthRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    internal abstract fun provideCommandeRepository(CommandeRepository: CommandeRepositoryImpl): CommandeRepository
}