package com.example.umc.db

import androidx.room.*

@Dao
interface DataDao {

    @Query("SELECT * FROM data_table ORDER BY id ASC")
    fun getAllDataASC() : List<DataEntity>

    @Query("SELECT * FROM data_table ORDER BY id DESC")
    fun getAllDataDESC() : List<DataEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data : DataEntity)

    @Query("DELETE FROM data_table")
    fun deleteAllPictures()
}