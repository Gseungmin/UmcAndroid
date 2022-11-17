package com.example.umc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.umc.databinding.ActivitySplashBinding
import com.example.umc.viewmodel.LoadingViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: LoadingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Handler(Looper.myLooper()!!).postDelayed({
            binding.progressBar.isVisible = false
            binding.textViewIsLoading.isVisible = false
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}