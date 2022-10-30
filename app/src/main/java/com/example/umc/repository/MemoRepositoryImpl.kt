package com.example.umc.repository

import com.example.umc.db.MemoDatabase
import com.example.umc.db.MemoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * data의 출처와 관계 없이 동일한 인터페이스로 데이터에 접근할 수 있도록 하는 패턴
 * Repository를 사용함으로 ViewModel과 Data Layer(ROOM, Retrofit)와의 결합이 낮춰짐
 * */
class MemoRepositoryImpl(
    //생성자로 MemoDatabase를 받아서 Dao를 통해 각 메서드 구현
    //그 후 MainActivity에서 Repository 생성시 MemoDatabase 객체를 전달
    private val db: MemoDatabase,
) : MemoRepository {

    override suspend fun insertMemo(memo: MemoEntity) {
        db.memoDao().insertMemo(memo)
    }

    override suspend fun deleteMemo(memo: MemoEntity) {
        db.memoDao().deleteMemo(memo)
    }

    override fun getAllMemo(): List<MemoEntity> {
        return db.memoDao().getMemos()
    }
}