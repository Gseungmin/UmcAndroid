package com.example.umc.repository

import android.util.Log
import com.example.umc.Constants
import com.example.umc.retrofit.instance.RetrofitInstance.serverApi
import com.example.umc.retrofit.instance.AccessRetrofitInstance.accessTokenApi
import com.example.umc.retrofit.dto.LoginGoogleRequestModel
import com.example.umc.retrofit.dto.LoginGoogleResponseModel
import com.example.umc.retrofit.dto.SendAccessTokenModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

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

    fun login(idToken: String, case: String): Call<ResponseBody> {
        return serverApi.login(idToken,case)
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

    fun home(): Call<String> {
        val returnValue = serverApi.home()
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

    fun test(accessToken: String): Call<Objects> {
        val returnValue = serverApi.test(accessToken)
        returnValue.enqueue(object : Callback<Objects> {
            override fun onResponse(call: Call<Objects>, response: Response<Objects>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<Objects>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
        return returnValue
    }

    fun kakaoLogin(accessToken: String): Call<Objects> {
        val returnValue = serverApi.kakaoLogin(accessToken)
        returnValue.enqueue(object : Callback<Objects> {
            override fun onResponse(call: Call<Objects>, response: Response<Objects>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<Objects>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
        return returnValue
    }

    fun googleLogin(accessToken: String): Call<Objects> {
        val returnValue = serverApi.googleLogin(accessToken)
        returnValue.enqueue(object : Callback<Objects> {
            override fun onResponse(call: Call<Objects>, response: Response<Objects>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<Objects>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
        return returnValue
    }

    fun check(): Call<Objects> {
        val returnValue = serverApi.check()
        returnValue.enqueue(object : Callback<Objects> {
            override fun onResponse(call: Call<Objects>, response: Response<Objects>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<Objects>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
        return returnValue
    }

    fun please(): Call<String> {
        val returnValue = serverApi.please()
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