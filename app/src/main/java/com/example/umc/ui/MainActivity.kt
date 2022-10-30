package com.example.umc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.umc.databinding.ActivityMainBinding
import com.example.umc.db.MemoDatabase
import com.example.umc.repository.MemoRepositoryImpl

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = MemoDatabase.getInstance(this)
        //datastore 의존성을 repository에 전달
        val memoRepository = MemoRepositoryImpl(database)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}