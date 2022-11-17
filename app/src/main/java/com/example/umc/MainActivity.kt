package com.example.umc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.umc.databinding.ActivityMainBinding
import com.example.umc.viewmodel.NumViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: NumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //viewModel 초기화
        viewModel = ViewModelProvider(this).get(NumViewModel::class.java)

        binding.button.setOnClickListener {
            //타이머 시작
            viewModel.timerStart()
        }

        viewModel.num.observe(this) {
            //타이머가 진행중인 경우
            if (viewModel.start.value!!.equals(true)) {
                binding.textView.text = it.toString()
                if (!binding.editText.text.toString().equals("")) { //NPE 문제 해결을 위한 로직
                    if (it > binding.editText.text.toString().toInt()) {
                        binding.textView.text = "완료되었습니다"
                        viewModel.init()
                    }
                }
            } else {
                binding.textView.text = "Time"
            }
        }
    }
}