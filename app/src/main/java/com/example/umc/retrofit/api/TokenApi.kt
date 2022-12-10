package com.example.umc.retrofit.api

import com.example.umc.retrofit.dto.SendAccessTokenModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenApi {

    //인증에 필요한 Headers와 Get요청 주소 정의
    @POST("token")
    fun postToken(@Header("Authorization") idToken: String) : Call<String>

    @POST("login")
    @Headers("content-type: application/json")
    fun sendAccessToken(@Body accessToken: SendAccessTokenModel):Call<String>
}