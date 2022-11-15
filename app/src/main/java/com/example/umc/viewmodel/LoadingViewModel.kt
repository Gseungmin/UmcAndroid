package com.example.umc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingViewModel : ViewModel() {

    private var _countValue = MutableLiveData("0")
    val countValue : LiveData<String>
        get() = _countValue

    fun plus(){
        _countValue.value = _countValue.value?.toInt()?.plus(1).toString();
    }

    fun exec(){
        viewModelScope.launch {
            for(i in 0..99) {
                if (i < 5) {
                    delay(160)
                } else if (i < 12) {
                    delay(120)
                } else if (i < 20) {
                    delay(50)
                } else if (i < 78) {
                    delay(10)
                } else if (i < 100) {
                    delay(2)
                }
                plus()
            }
            _countValue.value = "로딩 중"
        }
    }
}