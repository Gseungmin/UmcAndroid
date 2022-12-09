package com.example.umc.api

import com.example.umc.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

object RetrofitInstance {

    private val okHttpClient: OkHttpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient).baseUrl(BASE_URL).build() //build로 객체 생성
    }

    val api: TokenApi by lazy {
        retrofit.create(TokenApi::class.java)
    }

//    /**
//     * 토큰 객체 전달
//     * */
//    class HeaderInterceptor(private val token: String) : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val token = "Bearer $token"
//            val newRequest = chain.request().newBuilder()
//                .addHeader("Authorization", token)
//                .build()
//            return chain.proceed(newRequest)
//        }
//    }
}