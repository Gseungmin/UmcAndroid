package com.example.umc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.example.umc.R
import com.example.umc.databinding.ActivityLoginBinding
import com.example.umc.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.google.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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