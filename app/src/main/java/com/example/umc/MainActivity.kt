package com.example.umc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.umc.databinding.ActivityMainBinding
import android.widget.Toast
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    var isRunning = false
    var timer: Timer? = null
    var time = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnStart.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start -> {
                if (isRunning) {
                    pause()
                } else {
                    start()
                }
            }
        }
    }

    private fun start() {
        binding.btnStart.text = "일시정지"

        binding.btnStart.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        timer = timer(period = 1) {
            time--

            val milli_second = -(time % 100)
            val second = 30 + (time % 6000) / 100

            runOnUiThread {
                if (isRunning) {
                    binding.tvMillisecond.text = ".${milli_second}"
                    binding.tvSecond.text = "${second}"
                    if (milli_second == 0 && second == 0) {
                        stop()
                    }
                }
            };
        }
    }

    private fun stop() {
        binding.btnStart.text = "종료"
        binding.btnStart.setBackgroundColor(getColor(R.color.black))
        isRunning = false
        Toast.makeText(this, "종료되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun pause() {
        binding.btnStart.text = "시작"
        binding.btnStart.setBackgroundColor(getColor(R.color.blue))

        isRunning = false
        timer?.cancel()
    }
}