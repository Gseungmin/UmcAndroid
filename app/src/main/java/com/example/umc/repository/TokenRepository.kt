package com.example.umc.repository

import android.util.Log
import com.example.umc.Constants
import com.example.umc.retrofit.instance.RetrofitInstance.serverApi
import com.example.umc.retrofit.instance.AccessRetrofitInstance.accessTokenApi
import com.example.umc.retrofit.dto.LoginGoogleRequestModel
import com.example.umc.retrofit.dto.LoginGoogleResponseModel
import com.example.umc.retrofit.dto.SendAccessTokenModel
import com.example.umc.retrofit.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenRepository() {

    //client를 통해 TokenApi의 메서드 실행
    fun sendIdToken(idToken: String): Call<String> {
        val postIdToken = serverApi.postToken(idToken)
        postIdToken.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
        return postIdToken
    }

    fun getAccessToken(authCode:String) {
        accessTokenApi.getAccessToken(
            request = LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = Constants.GClientId,
                client_secret = Constants.GClientSecret,
                code = authCode.orEmpty()
            )
        ).enqueue(object : Callback<LoginGoogleResponseModel> {
            override fun onResponse(call: Call<LoginGoogleResponseModel>, response: Response<LoginGoogleResponseModel>) {
                if(response.isSuccessful) {
                    val accessToken = response.body()?.access_token.orEmpty()
                    Log.d(TAG, "accessToken: $accessToken")
                    sendAccessToken(accessToken)
                }
            }
            override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }
        })
    }

    fun sendAccessToken(accessToken:String){
        serverApi.sendAccessToken(
            accessToken = SendAccessTokenModel(accessToken)
        ).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    Log.d(TAG, "sendOnResponse: ${response.body()}")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
            }
        })
    }

    fun login(userDto: UserDto): Call<UserDto> {
        val returnValue = serverApi.login(userDto)
        returnValue.enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
        return returnValue
    }

    fun index(): Call<String> {
        val returnValue = serverApi.index()
        returnValue.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
        return returnValue
    }

    fun kakao(): Call<String> {
        val returnValue = serverApi.kakao()
        returnValue.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
        return returnValue
    }

    companion object {
        const val TAG = "LoginRepository"
    }
}