package com.example.umc.viewmodel

import android.graphics.Bitmap
import android.net.Uri
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
}