package com.example.umc.api

import com.google.android.gms.auth.api.credentials.IdToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {

    //인증에 필요한 Headers와 Get요청 주소 정의
    @POST("token")
    fun postToken(
        @Header("Authorization") idToken: String) : Call<String>
}