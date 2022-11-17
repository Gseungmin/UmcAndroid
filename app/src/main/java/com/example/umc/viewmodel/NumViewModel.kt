package com.example.umc.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NumViewModel() : ViewModel() {

    private var _num = MutableLiveData(0)
    val num : LiveData<Int>
        get() = _num

    private var _start = MutableLiveData(false)
    val start : LiveData<Boolean>
        get() = _start

    fun timerStart() {
        _start.value = true

        viewModelScope.launch {
            //타이머가 눌려져 있으면 1초에 1씩 증가
            while(start.value!!.equals(true)) {
                _num.value = _num.value!!.plus(1)
                delay(1000)
            }
        }
    }

    fun init() {
        _start.value = false
        _num.value = 0
    }
}