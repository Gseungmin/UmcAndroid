package com.example.umc.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinstudy.dataStore.MyDataStore
import com.example.umc.repository.TokenRepository
import com.example.umc.retrofit.dto.UserDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyInfoViewModel() : ViewModel() {

    private val repository = TokenRepository()

    private var _name = MutableLiveData<String>()
    val name : LiveData<String>
        get() = _name

    private var _email = MutableLiveData<String>()
    val email : LiveData<String>
        get() = _email

    /**
     * MYPAGE의 정보를 가지고 옴
     * */
    fun getMyInfo() = viewModelScope.launch {
        val tokenValue = MyDataStore().getAccessToken().first()

        val response =  repository.userInfo(tokenValue)
        response.enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if (response.isSuccessful) {
                    _email.value = response.body()?.username
                    _name.value = response.body()?.username
                    Log.d("RESPONSE", "SUCCESS")
                } else {
                    Log.d("RESPONSE", "FAIL")
                }
            }
            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Log.d("ContinueFail", "FAIL")
            }
        })
    }
}