package com.example.umc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.umc.repository.DataRepository
import com.example.umc.repository.LoginRepository

/**
 * ViewModel에 초기 값을 전달하기 위한 Factory
 * */
class LoginViewModelFactory(private val dataRepository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataRepository) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }
}