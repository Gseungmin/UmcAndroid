package com.example.umc.repository

import com.example.umc.db.DataBase
import com.example.umc.db.DataEntity

class DataRepository(private val db: DataBase) {

    fun getAllData() = db.dataDao().getAllData()

    fun insertData(dataEntity: DataEntity) =
        db.dataDao().insert(dataEntity)

    fun deleteAll() =
        db.dataDao().deleteAllPictures()
}