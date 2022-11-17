package com.example.umc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val num : Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NumViewModel::class.java)) {
            return NumViewModel(num) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }
}