package com.example.umc.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc.repository.TokenRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenViewModel() : ViewModel() {

    private val repository = TokenRepository()

    private var _accessToken = MutableLiveData<String>()
    val accessToken : LiveData<String>
        get() = _accessToken

    fun sendIdToken(idToken: String) = viewModelScope.launch {
        repository.sendIdToken(idToken)
    }

    fun getAccessToken(authCode:String) = viewModelScope.launch {
        repository.getAccessToken(authCode)
    }

    fun login(idToken: String, case: String) = viewModelScope.launch {
        val response = repository.login(idToken, case)
        response.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE", response.body().toString())
                    _accessToken.value = response.headers().get("Authorization").toString()
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
    }

    fun index() = viewModelScope.launch {
        repository.index()
    }

    fun kakao() = viewModelScope.launch {
        repository.kakao()
    }

    fun home() = viewModelScope.launch {
        repository.home()
    }

    fun test(accessToken: String) = viewModelScope.launch {
        repository.test(accessToken)
    }

    fun kakaoLogin(accessToken: String) = viewModelScope.launch {
        repository.kakaoLogin(accessToken)
    }

    fun googleLogin(accessToken: String) = viewModelScope.launch {
        repository.googleLogin(accessToken)
    }

    fun check() = viewModelScope.launch {
        repository.check()
    }

    fun please() = viewModelScope.launch {
        repository.please()
    }
}