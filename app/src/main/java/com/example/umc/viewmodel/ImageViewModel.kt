package com.example.umc.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc.db.DataEntity
import com.example.umc.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewModel(private val repository: DataRepository) : ViewModel() {

    private var _imageList = MutableLiveData<List<DataEntity>>()
    val imageList : LiveData<List<DataEntity>>
        get() = _imageList

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        _imageList.postValue(repository.getAllData())
    }

    fun insertData(bitmap: Bitmap, title: String, location: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val dataEntity = DataEntity(0,title,location,bitmap)
            repository.insertData(dataEntity)
            Log.d("OUTPUTPOST", dataEntity.toString())
    }
}