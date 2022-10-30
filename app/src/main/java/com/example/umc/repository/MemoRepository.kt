package com.example.umc.repository

import com.example.umc.db.MemoEntity

/**
 * data의 출처와 관계 없이 동일한 인터페이스로 데이터에 접근할 수 있도록 하는 패턴
 * Repository를 사용함으로 ViewModel과 Data Layer(ROOM, Retrofit)와의 결합이 낮춰짐
 * */
interface MemoRepository {

    //Room Dao를 조작하기 위한 메소드
    suspend fun insertMemo(memo: MemoEntity)
    suspend fun deleteMemo(memo: MemoEntity)
    fun getAllMemo(): List<MemoEntity>
}