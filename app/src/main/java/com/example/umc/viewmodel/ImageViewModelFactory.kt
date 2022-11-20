package com.example.umc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.umc.repository.DataRepository

/**
 * ViewModel에 초기 값을 전달하기 위한 Factory
 * */
class ImageViewModelFactory(private val dataRepository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(dataRepository) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }
}