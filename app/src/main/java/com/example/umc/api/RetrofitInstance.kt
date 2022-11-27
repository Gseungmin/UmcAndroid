package com.example.umc.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * Retrofit 객체로 API를 호출해주는 객체
 * object와 lazy키워드로 구현함으로 실제 사용되는 순간 단 하나의 Retrofit 인스턴스가 만들어지도록 싱글톤으로 구현
 * 여러개의 Retrofit 객체가 만들어지면 자원도 낭비되고 통신에 혼선이 발생하므로 LAZY를 사용함, 스프링의 LazyLoading과 비슷
 * DTO 변환에 사용할 MoshiConverterFactory를 Json Converter로 지정
 * OkHttpCore는 서버와 애플리케이션 사이에서 데이터를 인터셉터하는 기능이 있어 로그 캣에서 패킷 내용 모니터링
 * create 명령어를 통해 MemberLoginApi의 Instance 생성
 */
object RetrofitInstance {

    private val okHttpClient: OkHttpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .addConverterFactory(MoshiConverterFactory.create())
//            .client(okHttpClient).baseUrl(BASE_URL).build() //build로 객체 생성
//    }
//
//    val api: BookSearchApi by lazy {
//        retrofit.create(BookSearchApi::class.java)
//    }

    fun getInstance() : Retrofit {
        return retrofit
    }
}