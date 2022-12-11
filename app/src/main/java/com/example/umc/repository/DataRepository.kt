package com.example.umc.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.umc.Sort
import com.example.umc.db.DataBase
import com.example.umc.db.DataEntity
import com.example.umc.repository.DataRepository.PreferencesKeys.SORT_MODE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataRepository(private val db: DataBase,
                     private val dataStore: DataStore<Preferences>) {

    fun getAllDataASC() = db.dataDao().getAllDataASC()
    fun getAllDataDESC() = db.dataDao().getAllDataDESC()

    fun insertData(dataEntity: DataEntity) =
        db.dataDao().insert(dataEntity)

    fun deleteAll() =
        db.dataDao().deleteAllPictures()

    // DataStore
    // 저장 및 불러오기에 사용할 키를 PreferencesKeys에 정의
    // 단순히 string을 사용하던 sharedPreference와 다르게 타입 안정을 위해 및 저장할 데이터가 String이기 때문에 stringPreferencesKey 사용
    private object PreferencesKeys {
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

    //저장 작업은 코루틴 안에서 이루어져야 하므로 suspend 사용 및 전달받을 Mode 값을 edit 블록 안에서 저장
    suspend fun saveSortMode(mode: String) {
            dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    //파일 접근을 위해서는 data 메소드 사용
    //캐치로 예외처리하고 웹 블록 안에서 키를 전달하여 플로우로 반환받으면 됨
    suspend fun getSortMode(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                //기본값은 ACCURACY
                prefs[SORT_MODE] ?: Sort.ACCURACY.value
            }
    }
}