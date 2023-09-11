package com.example.w02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.w02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)
        main.btnAdd.setOnClickListener {
            main.txtCount.text = "눌렀습니다."
        }
    }

}