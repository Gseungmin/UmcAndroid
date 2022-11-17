package com.example.umc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NumViewModel(input: Int) : ViewModel() {

    private var _num = MutableLiveData(0)
    val num : LiveData<Int>
        get() = _num

    private var _end = MutableLiveData(input)
    val end : LiveData<Int>
        get() = _end

    fun timerStart() {
        _num.value = 0
        viewModelScope.launch {
            //타이머가 눌려져 있으면 1초에 1씩 증가
            while(num.value!! < end.value!!) {
                _num.value = _num.value!!.plus(1)
                delay(1000)
            }
        }
    }
}