package com.kvad.totalizator.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.kvad.totalizator.chat.data.ChatService
import com.kvad.totalizator.chat.ui.ChatViewModel
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.tools.API_URL
import com.kvad.totalizator.tools.HeaderInterceptor
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
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
    fun provideRetrofit(headerInterceptor: HeaderInterceptor) : Retrofit {
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
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideEventService(retrofit: Retrofit): EventService {
        return retrofit.create(EventService::class.java)
    }

    @Singleton
    @Provides
    fun provideChatService(retrofit: Retrofit): ChatService {
        return retrofit.create(ChatService::class.java)
    }
}
