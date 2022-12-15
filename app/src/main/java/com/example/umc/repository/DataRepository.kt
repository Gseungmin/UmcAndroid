package com.example.umc.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.umc.Sort
import com.example.umc.db.DataBase
import com.example.umc.db.DataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataRepository(private val db: DataBase) {

    fun getAllDataASC() = db.dataDao().getAllDataASC()
    fun getAllDataDESC() = db.dataDao().getAllDataDESC()

    fun insertData(dataEntity: DataEntity) =
        db.dataDao().insert(dataEntity)

    fun deleteAll() =
        db.dataDao().deleteAllPictures()
}