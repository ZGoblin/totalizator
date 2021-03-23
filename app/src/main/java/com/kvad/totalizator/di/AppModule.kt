package com.kvad.totalizator.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.api.EventMockService
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.tools.API_URL
import com.kvad.totalizator.tools.HeaderInterceptor
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun context(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideEventRepository(): EventRepository {
        return EventRepository(EventMockService())
    }

    @Singleton
    @Provides
    fun provideUserService(headerInterceptor: HeaderInterceptor): UserService {
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(headerInterceptor)
                    .build()
            )
            .baseUrl(API_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
            .create(UserService::class.java)
    }

}
