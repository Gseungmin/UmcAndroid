package com.example.umc.repository

import com.example.umc.api.LoginApi
import com.example.umc.api.RetrofitInstance
import com.example.umc.db.DataBase
import com.example.umc.db.DataEntity

class LoginRepository() {

    //RetrofitInstance를 받아옴, 해당 client는 LoginApi의 메서드를 실행할 수 있음
    private val client = RetrofitInstance.getInstance().create(LoginApi::class.java)

    //client를 통해 MyApi의 메서드 실행
    suspend fun Login() = client.socialLogin()
}