package com.example.umc.retrofit.api

import com.example.umc.retrofit.dto.LoginGoogleRequestModel
import com.example.umc.retrofit.dto.LoginGoogleResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AccessTokenApi {

    @POST("oauth2/v4/token")
    fun getAccessToken(@Body request: LoginGoogleRequestModel): Call<LoginGoogleResponseModel>
}