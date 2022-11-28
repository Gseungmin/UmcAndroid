package com.example.umc.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DataEntity::class], version = 9, exportSchema = false)
@TypeConverters(ImageConverter::class)
abstract class DataBase : RoomDatabase() {

    abstract fun dataDao() : DataDao

    companion object {
        @Volatile
        private var INSTANCE : DataBase? = null

        fun getDatabase(
            context : Context
        ) : DataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "data_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}