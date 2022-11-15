package com.example.umc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel() : ViewModel() {

    private var _isRunning = MutableLiveData(false)
    val isRunning : LiveData<Boolean>
        get() = _isRunning

    private var _time = MutableLiveData(30)
    val time : LiveData<Int>
        get() = _time

    //타이머 시작 함수
    fun startTimer() {
        _isRunning.value = true

        viewModelScope.launch {
            while (isRunning.value == true && time.value!! > 0) {
                delay(1000)
                timeDown()
            }
            if (time.value!!.equals(0)) {
                stopTimer()
            }
        }
    }

    //타이머 일시정지 함수
    fun stopTimer(){
        _isRunning.value = false
    }

    //1초 시간 지남 함수
    fun timeDown() {
        _time.value = _time.value?.minus(1);
    }
}