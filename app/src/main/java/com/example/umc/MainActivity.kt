package com.example.umc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.umc.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.umc.viewmodel.TimerViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //viewModel 초기화
        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)

        //isRunning livedata observe
        viewModel.isRunning.observe(this, Observer {
            if (!it) {
                if (viewModel.time.value == 0) {
                    pause()
                } else {
                    start()
                }
            } else {
                if (viewModel.time.value == 0) {
                    pause()
                } else {
                    stop()
                }
            }
        })

        //time livedata observe
        viewModel.time.observe(this, Observer {
            binding.tvSecond.text = it.toString()
        })

        binding.btnStart.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start -> {
                if (viewModel.isRunning.value!!.equals(true)) {
                    viewModel.stopTimer()
                } else {
                    viewModel.startTimer()
                }
            }
        }
    }

    private fun start() {
        binding.btnStart.text = "시작"
        binding.btnStart.setBackgroundColor(getColor(R.color.blue))
    }

    private fun stop(){
        binding.btnStart.text = "일시정지"
        binding.btnStart.setBackgroundColor(getColor(R.color.red))
    }

    private fun pause() {
        binding.btnStart.text = "종료"
        binding.btnStart.setBackgroundColor(getColor(R.color.black))
        Toast.makeText(this, "종료되었습니다", Toast.LENGTH_SHORT).show()
    }
}