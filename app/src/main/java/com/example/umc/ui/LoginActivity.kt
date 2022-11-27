package com.example.umc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.umc.R
import com.example.umc.databinding.ActivityLoginBinding
import com.example.umc.databinding.ActivityMainBinding
import com.example.umc.repository.DataRepository
import com.example.umc.repository.LoginRepository
import com.example.umc.viewmodel.ImageViewModel
import com.example.umc.viewmodel.ImageViewModelFactory
import com.example.umc.viewmodel.LoginViewModel
import com.example.umc.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val loginRepository = LoginRepository()
        val factory = LoginViewModelFactory(loginRepository)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.google.setOnClickListener{
            viewModel.login()
        }

        binding.kakao.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.naver.setOnClickListener{
            startActivity(Intent(this, MapActivity::class.java))
            finish()
        }
    }
}