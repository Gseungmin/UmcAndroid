package com.example.coinstudy.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.umc.App

class MyDataStore {

    /**
     * AppContext 가지고 옴
     * */
    private val context = App.context()

    /**
     * dataStore 객체 전역 변수로 초기화
     * */
    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val mDataStore : DataStore<Preferences> = context.dataStore

    private val FIRST_FLAG = booleanPreferencesKey("FIRST_FLAG")

    /**
     * First Flag의 값을 변경하는 함수
     * 따라서 메인 엑티비티로 갈때 True로 변경
     * 따라서 처음 접속하는 User가 아니면 True, 처음 접속하는 User이면 False
     * */
    suspend fun setupFirstData() {
        mDataStore.edit {
            preferences ->
            preferences[FIRST_FLAG] = true
        }
    }

    /**
     * 현재 First Flag 값을 가지고오는 함수
     * */
    suspend fun getFirstData() : Boolean {

        var currentValue = false

        //처음 접속한 user는 false, 아니면 true 리턴
        mDataStore.edit {
                preferences ->
            currentValue = preferences[FIRST_FLAG] ?: false
        }

        return currentValue
    }
}