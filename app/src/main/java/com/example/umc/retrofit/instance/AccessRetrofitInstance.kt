package com.example.umc.retrofit.instance

import com.example.umc.Constants.GoogleAccessTokenUri
import com.example.umc.retrofit.api.AccessTokenApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object AccessRetrofitInstance {

    private val okHttpClient: OkHttpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    private val retrofit: Retrofit by lazy {

        val gson = GsonBuilder().setLenient().create()

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient).baseUrl(GoogleAccessTokenUri).build() //build로 객체 생성
    }

    val accessTokenApi: AccessTokenApi by lazy {
        retrofit.create(AccessTokenApi::class.java)
    }
}