package com.example.umc.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc.db.DataEntity
import com.example.umc.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageViewModel(private val repository: DataRepository) : ViewModel() {

    private var _imageList = MutableLiveData<List<DataEntity>>()
    val imageList : LiveData<List<DataEntity>>
        get() = _imageList
    
    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        if (getSortMode() == "accuracy") {
            _imageList.postValue(repository.getAllDataASC())
        } else if (getSortMode() == "latest") {
            _imageList.postValue(repository.getAllDataDESC())
        }
    }

    fun getMyPicture() = viewModelScope.launch(Dispatchers.IO) {
        _imageList.postValue(repository.getAllDataASC())
    }

    fun insertData(bitmap: Bitmap, title: String, location: String, date: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val dataEntity = DataEntity(0,title,location,bitmap,date)
            repository.insertData(dataEntity)
    }

    fun deleteData() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }

    private var _datas = MutableLiveData<List<Uri>>()
    val datas : LiveData<List<Uri>>
        get() = _datas

    fun setImage(images: List<Uri>) {
        _datas.value = images
    }

    //DataStore
    //값을 저장
    //repository의 saveSortMode를 viewModelScope에서 실행하되 IO 작업이므로 Dispatchers를 IO로 설정
    fun saveSortMode(value: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveSortMode(value)
    }

    //값을 불러옴
    suspend fun getSortMode() = withContext(Dispatchers.IO) {
        //설정 값 특성상 전체 데이터 스트림을 가져올 필요 없음
        //withContext는 반드시 값을 반환하고 종료됨
        repository.getSortMode().first()
    }
}