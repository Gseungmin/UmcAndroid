package com.example.umc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.umc.databinding.ActivityMainBinding
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var handler = Handler(Looper.getMainLooper())

        binding.button.setOnClickListener {
            val num = binding.editText.text.toString()
            var start = true
            Thread(){
                while(start) {
                    for (i in 1 until parseInt(num) + 1) {
                        handler.post {
                            binding.textView.text = i.toString()
                        }
                        Thread.sleep(1000);
                    }
                    start =false;
                    handler.post {
                        binding.textView.text = "Finish";
                    }
                }
            }.start()
        }
    }
}