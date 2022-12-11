package com.example.umc.retrofit.api

import com.example.umc.Constants.Access
import com.example.umc.Constants.GoogleAccessTokenUri
import com.example.umc.Constants.googleeAccess
import com.example.umc.Constants.idToken
import com.example.umc.Constants.kakaoToken
import com.example.umc.retrofit.dto.SendAccessTokenModel
import com.example.umc.retrofit.dto.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenApi {

    //인증에 필요한 Headers와 Get요청 주소 정의
    @POST("token")
    fun postToken(@Header("Authorization") idToken: String) : Call<String>

    @POST("accessToken")
    @Headers("content-type: application/json")
    fun sendAccessToken(@Body accessToken: SendAccessTokenModel):Call<String>

    @POST("login")
    @Headers("content-type: application/json")
    fun login(@Body userDto: UserDto):Call<UserDto>

    @POST("/google")
    @Headers("content-type: application/json", "Authorization:Bearer ${idToken}")
    fun index():Call<String>

    @POST("/kakao")
    @Headers("content-type: application/json", "Authorization:Bearer ${kakaoToken}")
    fun kakao():Call<String>

    @GET("/")
    @Headers("content-type: application/json", "Authorization:Bearer ${Access}")
    fun home():Call<String>

    @GET("/testToken")
    @Headers("content-type: application/json", "Authorization:Bearer ${idToken}")
    fun test():Call<String>
}