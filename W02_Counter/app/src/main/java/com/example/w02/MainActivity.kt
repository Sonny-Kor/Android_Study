package com.example.w02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.w02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)
        main.btnAdd.setOnClickListener {
            count += 1
            main.txtCount.text = count.toString()
        }
        main.btnSubtract.setOnClickListener{
            if(count > 0)
                main.txtCount.text = "${--count}"
        }
        main.btnReset.setOnClickListener {
            count = 0
            main.txtCount.text = count.toString()
        }
    }
}