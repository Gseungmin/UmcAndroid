package com.example.umc.db

import androidx.room.*

/**
 * database를 조작할 쿼리를 날리는 역할
 * 데이터 접근 객체, Controller라고 생각하면 됨
 * */
@Dao
interface MemoDao {

    //쿼리를 제외한 CRUD 작업은 시간이 걸리는 작업이므로 suspend로 선언하여 코루틴 안에서 비동기적으로 사용
    //동일한 PK가 있는 경우 덮어쓰기
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: MemoEntity)

    @Delete
    suspend fun deleteMemo(memo: MemoEntity)

    @Query("SELECT * FROM memo_table")
    fun getMemos(): List<MemoEntity>
}