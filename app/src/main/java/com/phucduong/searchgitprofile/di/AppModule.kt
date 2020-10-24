package com.phucduong.searchgitprofile.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.phucduong.searchgitprofile.BuildConfig
import com.phucduong.searchgitprofile.Constant
import com.phucduong.searchgitprofile.data.LocalDataSource
import com.phucduong.searchgitprofile.data.RemoteDataSource
import com.phucduong.searchgitprofile.data.Repository
import com.phucduong.searchgitprofile.data.UserRepository
import com.phucduong.searchgitprofile.data.local.UserDatabase
import com.phucduong.searchgitprofile.data.local.UserLocalDataSource
import com.phucduong.searchgitprofile.data.remote.ApiServices
import com.phucduong.searchgitprofile.data.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
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
    fun providesUserRemoteDataSource(apiServices: ApiServices) : RemoteDataSource {
        return UserRemoteDataSource(apiServices)
    }

    @Provides
    @Singleton
    fun providesApiServices(retrofit: Retrofit) : ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofit(httpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://api.openUsermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: Interceptor) : OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun providesAuthInterceptor() = Interceptor { chain ->
            val newUrl =
                chain.request().url().newBuilder().addQueryParameter("appId", Constant.USER_APP_ID)
                    .build()
            val newRequest = chain.request().newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
    }

    @Provides
    fun provideLocalDataSource(database: UserDatabase, ioDispatcher: CoroutineDispatcher): LocalDataSource {
        return UserLocalDataSource(database.userDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "User.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context) = context
                .getSharedPreferences(Constant.PREFERENCES, MODE_PRIVATE)
}

@Module
@InstallIn(ApplicationComponent::class)
object UserRepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource, sharedPreferences: SharedPreferences): Repository {
        return UserRepository(localDataSource, remoteDataSource, sharedPreferences)
    }
}