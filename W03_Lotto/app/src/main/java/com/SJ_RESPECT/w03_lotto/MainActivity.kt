package com.SJ_RESPECT.w03_lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.SJ_RESPECT.w03_lotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var main : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        main = ActivityMainBinding.inflate(layoutInflater) // infate 는 객체를 메모리에 올리는것
        setContentView(main.root)

        main.btnGenerate.setOnClickListener {

            main.num1.text = Random.nextInt(1,46).toString()
            main.num2.text = Random.nextInt(1,46).toString()
            main.num3.text = Random.nextInt(1,46).toString()
            main.num4.text = Random.nextInt(1,46).toString()
            main.num5.text = Random.nextInt(1,46).toString()
            main.num6.text = Random.nextInt(1,46).toString()

        }
    }
}