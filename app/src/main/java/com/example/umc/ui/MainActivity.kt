package com.example.umc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.umc.R
import com.example.umc.databinding.ActivityMainBinding
import com.example.umc.slider.CardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navigation()
    }

    private fun navigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment? ?: return
        navController = host.navController
        binding.navBottom.setupWithNavController(navController)
    }
}