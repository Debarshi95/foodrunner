package com.example.dev.foodrunner.di.app_scope

import com.example.dev.foodrunner.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    init {
        System.loadLibrary("keys")
    }


    external fun getApiKey(): String

    @Provides
    @Singleton
    @Named("apiKey")
    fun providesAPIKey() = getApiKey()

    companion object {

        @Provides
        @Named("BASE_URL")
        @JvmStatic
        fun providesBaseURL(): String = "http://13.235.250.119/v2/"

        @Provides
        @JvmStatic
        fun providesRetrofit(
            okHttpClient: OkHttpClient,
            @Named("BASE_URL") baseUrl: String
        ): Retrofit {
            return Retrofit.Builder().apply {
                baseUrl(baseUrl)
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create())
            }.build()
        }

        @Provides
        @JvmStatic
        fun providesApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }


        @Provides
        @JvmStatic
        fun providesOkHttpClientBuilder() = OkHttpClient.Builder()

        @Provides
        @JvmStatic
        fun providesOkHttpClient(
            okHttpClientBuilder: OkHttpClient.Builder,
            @Named("apiKey") apiKey: String
        ): OkHttpClient {
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


}