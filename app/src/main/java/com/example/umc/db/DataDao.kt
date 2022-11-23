package com.example.umc.db

import androidx.room.*

@Dao
interface DataDao {

    @Query("SELECT * FROM data_table")
    fun getAllData() : List<DataEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data : DataEntity)

    @Query("DELETE FROM data_table")
    fun deleteAllPictures()
}