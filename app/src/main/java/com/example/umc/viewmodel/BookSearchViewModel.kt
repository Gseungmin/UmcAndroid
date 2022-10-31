package com.example.umc.viewmodel

import androidx.lifecycle.*
import com.example.umc.db.MemoEntity
import com.example.umc.repository.MemoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel 객체, Repository로 부터 데이터를 받아와서 처리, 따라서 Factory 필요
 * */
class BookSearchViewModel(
    private val memoRepository: MemoRepository
) : ViewModel() {

    // CRUD를 수행하는 suspend 함수는 viewModelScope에서 실행하게 하여 viewModelScope의 기본 Dispatchers가 Main이므로 IO로 바꿔준다
    fun saveMemo(memo: MemoEntity) = viewModelScope.launch(Dispatchers.IO) {
        memoRepository.insertMemo(memo)
    }
    fun deleteMemo(memo: MemoEntity) = viewModelScope.launch(Dispatchers.IO) {
        memoRepository.deleteMemo(memo)
    }
    fun getAllMemo() = viewModelScope.launch(Dispatchers.IO){
        memoRepository.getAllMemo();
    }
}