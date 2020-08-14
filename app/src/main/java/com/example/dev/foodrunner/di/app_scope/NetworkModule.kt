package com.example.dev.foodrunner.di.app_scope

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NetworkModule @Inject constructor() {

    init {
        System.loadLibrary("keys")
    }


    external fun getApiKey(): String


    @Provides
    @Singleton
    fun getAPIKey() = getApiKey()

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }


    @Provides
    @Singleton
    fun getBaseURL(): String = "http://13.235.250.119/v2"

    @Provides
    @Singleton
    fun getOkHttpClientBuilder() = OkHttpClient.Builder()


    @Provides
    @Singleton
    fun getOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder, apiKey: String): OkHttpClient {
        return okHttpClientBuilder.apply {
            addInterceptor { chain: Interceptor.Chain ->
                val originalRequest = chain.request()
                val newReq = originalRequest.newBuilder().apply {
                    addHeader("content-type", "application/json")
                    addHeader("token", apiKey)
                }.build()

                return@addInterceptor chain.proceed(newReq)
            }

            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

}