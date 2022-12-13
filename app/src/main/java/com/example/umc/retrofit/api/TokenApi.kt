package com.example.umc.retrofit.api

import com.example.umc.Constants.Access
import com.example.umc.Constants.GoogleAccessTokenUri
import com.example.umc.Constants.googleeAccess
import com.example.umc.Constants.idToken
import com.example.umc.Constants.kakaoToken
import com.example.umc.retrofit.dto.SendAccessTokenModel
import com.example.umc.retrofit.dto.UserDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*
import java.util.Objects

interface TokenApi {

    //인증에 필요한 Headers와 Get요청 주소 정의
    @POST("token")
    fun postToken(@Header("Authorization") idToken: String) : Call<String>

    @POST("accessToken")
    @Headers("content-type: application/json")
    fun sendAccessToken(@Body accessToken: SendAccessTokenModel):Call<String>

    @POST("/login")
    @Headers("content-type: application/json")
    fun login(@Header("Authorization") idToken:
              String, @Header("LoginCase") case: String):Call<ResponseBody>

    @POST("/google")
    @Headers("content-type: application/json", "Authorization:Bearer ${idToken}")
    fun index():Call<String>

    @POST("/kakao")
    @Headers("content-type: application/json", "Authorization:Bearer ${kakaoToken}")
    fun kakao():Call<String>

    @GET("/")
    @Headers("content-type: application/json", "Authorization:Bearer ${Access}")
    fun home():Call<String>

    @GET("/api/user")
    @Headers("content-type: application/json")
    fun test(@Header("Authorization") accessToken: String):Call<Objects>

    @POST("/kakaoLogin")
    fun kakaoLogin(@Query("accessToken") param: String): Call<Objects>

    @POST("/googleLogin")
    fun googleLogin(@Query("authorizationCode") param: String): Call<Objects>

    @POST("/check")
    @Headers("content-type: application/json", "Authorization: ${idToken}")
    fun check():Call<Objects>

    @GET("/please")
    @Headers("content-type: application/json", "Authorization:Bearer ${Access}")
    fun please():Call<String>
}