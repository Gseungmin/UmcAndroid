package com.example.umc.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo_table")
data class MemoEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "memo")
    var memo : String,
    @ColumnInfo(name = "date")
    var date : String
)
