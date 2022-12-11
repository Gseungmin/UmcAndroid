package com.example.umc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc.repository.TokenRepository
import com.example.umc.retrofit.dto.UserDto
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

    fun login(userDto: UserDto) = viewModelScope.launch {
        repository.login(userDto)
    }

    fun index() = viewModelScope.launch {
        repository.index()
    }

    fun kakao() = viewModelScope.launch {
        repository.kakao()
    }
}