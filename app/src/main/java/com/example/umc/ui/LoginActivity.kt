package com.example.umc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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



    }
}