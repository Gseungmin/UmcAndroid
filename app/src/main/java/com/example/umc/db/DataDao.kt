package com.example.umc.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("SELECT * FROM data_table")
    fun getAllData() : List<DataEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data : DataEntity)
}