package com.example.umc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.umc.databinding.ActivityMainBinding
import com.example.umc.db.MemoDatabase
import com.example.umc.repository.MemoRepositoryImpl
import com.example.umc.viewmodel.MemoViewModel
import com.example.umc.viewmodel.MemoViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel: MemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = MemoDatabase.getInstance(this)
        //datastore 의존성을 repository에 전달
        val memoRepository = MemoRepositoryImpl(database)
        val factory = MemoViewModelFactory(memoRepository)
        //viewModel 초기화
        viewModel = ViewModelProvider(this, factory).get(MemoViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}