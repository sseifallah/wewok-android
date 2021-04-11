package com.delivery.wewok.di

import android.content.Context
import com.delivery.wewok.BuildConfig
import com.delivery.wewok.base.checkMainThread
import com.delivery.wewok.data.remote_servise.AuthService
import com.delivery.wewok.data.remote_servise.CommandeService
import com.delivery.wewok.data.remote_servise.WewokServise
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val DEFAULT_TIMEOUT = 100L
    private const val CACHE_SIZE = 10 * 1024 * 1024

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().apply {
            setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES)
            serializeNulls()
            setLenient()
        }.create()
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    internal fun provideCache(@ApplicationContext context: Context): Cache {
        return checkMainThread { Cache(context.cacheDir, CACHE_SIZE.toLong()) }
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return checkMainThread {
            OkHttpClient.Builder().apply {
                cache(cache)
                connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

                interceptors().add(httpLoggingInterceptor)
            }.build()
        }
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
        gson: Gson,
        okHttpClient: Lazy<OkHttpClient>
    ): Retrofit {
        return Retrofit.Builder().apply {
            val SERVER_URL = "https://www.wewok.net/"
            baseUrl(SERVER_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideWewokServise(retrofit: Retrofit):  WewokServise = retrofit.create()

    @Provides
    @Singleton
    internal fun provideAuthServise(retrofit: Retrofit):  AuthService = retrofit.create()

    @Provides
    @Singleton
    internal fun provideCommandeServise(retrofit: Retrofit):  CommandeService = retrofit.create()
}