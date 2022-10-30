package com.example.umc.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    //Room에서 사용할 엔티티 선언
    entities = [MemoEntity::class],
    version = 1,
)
abstract class MemoDatabase : RoomDatabase() {

    //Room에서 사용할 dao 지정
    abstract fun memoDao(): MemoDao

    //DB 객체도 생성하는데 비용이 많이 들기 때문에 중복으로 생성하지 않도록 싱글톤 설정
    companion object {

        //Java변수를 Main Memory에 저장하겠다는 것을 명시하는 것
        //Multi-Thread 환경을 대비하기 위해 사용
        @Volatile
        private var INSTANCE: MemoDatabase? = null

        private fun buildDatabase(context: Context): MemoDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                MemoDatabase::class.java,
                "memo_database"
            ).build()

        fun getInstance(context: Context): MemoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}