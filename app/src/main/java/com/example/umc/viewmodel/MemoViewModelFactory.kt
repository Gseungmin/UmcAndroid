package com.example.umc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.umc.repository.MemoRepository

class MemoViewModelFactory(private val memoRepository: MemoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MemoViewModel::class.java)) {
            return MemoViewModel(memoRepository) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }

}