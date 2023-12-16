package com.raed.yahoofinance.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raed.yahoofinance.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Raed Saeed on 12/16/2023
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    companion object {
        const val baseUrl = "https://yh-finance.p.rapidapi.com/"
    }

    @Provides
    fun getGson(): Gson {
        return GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
    }

    @Provides
    fun provideHeaderInterceptor() : HeaderInterceptor {
        return HeaderInterceptor()
    }

    @Provides
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.v(
                "logger",
                "log $message"
            )
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}