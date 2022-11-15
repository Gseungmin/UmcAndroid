package com.example.umc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.umc.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityMainBinding

    var isRunning = false
    var timer : Timer? = null
    var time = 30

    private lateinit var btn_start: Button
    private lateinit var tv_millisecond: TextView
    private lateinit var tv_second: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        btn_start = binding.btnStart
        tv_millisecond = binding.tvMillisecond
        tv_second = binding.tvSecond

        btn_start.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_start -> {
                if(isRunning){
                    pause()
                }else{
                    start()
                }
            }
        }
    }
    private fun start(){
        btn_start.text = "일시정지"
        btn_start.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        timer = timer(period = 1) {
            time--

            val milli_second = -(time % 100)
            val second = 30 + (time % 6000) / 100

            runOnUiThread {
                if (isRunning) {
                    tv_millisecond.text =  ".${milli_second}"
                    tv_second.text = "${second}"
                    if(milli_second == 0 && second == 0){
                        stop()
                    }
                }
            };
        }
    }


    private fun stop(){
        btn_start.text = "종료"
        btn_start.setBackgroundColor(getColor(R.color.black))
        isRunning = false
        Toast.makeText(this, "종료되었습니다", Toast.LENGTH_SHORT).show()
    }
    private fun pause(){
        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))

        isRunning = false
        timer?.cancel()
    }

}