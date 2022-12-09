package com.example.umc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc.repository.TokenRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenViewModel() : ViewModel() {

    private val repository = TokenRepository()

    fun sendToken(idToken: String) = viewModelScope.launch {
        val returnValue = repository.sendToken(idToken)

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
    }
}