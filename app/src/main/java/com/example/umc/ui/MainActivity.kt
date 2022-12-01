package com.example.umc.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.umc.Constants.DATASTORE_NAME
import com.example.umc.R
import com.example.umc.databinding.ActivityMainBinding
import com.example.umc.db.DataBase
import com.example.umc.repository.DataRepository
import com.example.umc.viewmodel.ImageViewModel
import com.example.umc.viewmodel.ImageViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var viewModel: ImageViewModel

    //dataStore의 싱글톤 객체 생성
    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val database = DataBase.getDatabase(this)
        val dataRepository = DataRepository(database, dataStore)
        val factory = ImageViewModelFactory(dataRepository)
        viewModel = ViewModelProvider(this, factory).get(ImageViewModel::class.java)

        navigation()
    }

    /**
     * Navigation 기능 + BottomNavigationView 기능 추가
     * menu에 맞게 Fragment이동하는 기능
     * */
    private fun navigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment? ?: return
        navController = host.navController
        binding.navBottom.setupWithNavController(navController)
    }
}