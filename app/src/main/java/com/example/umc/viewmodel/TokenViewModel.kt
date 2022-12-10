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

    fun sendIdToken(idToken: String) = viewModelScope.launch {
        repository.sendIdToken(idToken)
    }

    fun getAccessToken(authCode:String) = viewModelScope.launch {
        repository.getAccessToken(authCode)
    }
}