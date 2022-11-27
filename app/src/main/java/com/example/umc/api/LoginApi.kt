package com.example.umc.api

import retrofit2.http.GET

interface LoginApi {

    @GET("/oauth2/authorization/google")
    suspend fun socialLogin()
}