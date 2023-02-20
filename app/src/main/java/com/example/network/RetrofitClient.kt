package com.example.network

import android.view.PixelCopy.request
import com.example.interested.MainActivity_interest
import com.example.login.Login
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://helptheyouth-lope.com/app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // 로그캣에서 패킷 내용을 모니터링 할 수 있음 (인터셉터)
            .build()
    }

    val emgMedService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }

    fun getApiClient(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://helptheyouth-lope.com/app/")
            .client(provideOKHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOKHttpClient(interceptor: AppInterceptor): OkHttpClient
        = OkHttpClient.Builder().run{
            addInterceptor(interceptor)
            build()
    }

    class AppInterceptor: Interceptor{
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response = with(chain){
            val newRequest = request().newBuilder()
                .addHeader("Authorization","")
                .build()
            proceed(newRequest)
        }
    }
}