package com.example.umc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.example.umc.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Handler(Looper.myLooper()!!).postDelayed({
            binding.progressBar.isVisible = false
            binding.textViewIsLoading.isVisible = false
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }
}