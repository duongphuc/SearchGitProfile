package com.phucduong.searchgitprofile.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.phucduong.searchgitprofile.BuildConfig
import com.phucduong.searchgitprofile.data.RemoteDataSource
import com.phucduong.searchgitprofile.data.Repository
import com.phucduong.searchgitprofile.data.UserRepository
import com.phucduong.searchgitprofile.data.remote.ApiServices
import com.phucduong.searchgitprofile.data.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesUserRemoteDataSource(apiServices: ApiServices): RemoteDataSource {
        return UserRemoteDataSource(apiServices)
    }

    @Provides
    @Singleton
    fun providesApiServices(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
@InstallIn(ApplicationComponent::class)
object UserRepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(remoteDataSource: RemoteDataSource): Repository {
        return UserRepository(remoteDataSource)
    }
}