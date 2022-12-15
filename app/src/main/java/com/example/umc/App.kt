package com.example.umc

import android.app.Application
import android.content.Context

class App : Application() {

    /**
     * datastore를 위한 로직
     * */
    init {
        instance = this
    }

    companion object {
        private var instance : App? = null

        fun context() : Context {
            return instance!!.applicationContext
        }
    }
}