package com.example.umc.repository

import android.util.Log
import com.example.umc.api.RetrofitInstance.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenRepository() {

    //client를 통해 TokenApi의 메서드 실행
    fun sendToken(idToken: String): Call<String> {
        return api.postToken(idToken)
    }
}