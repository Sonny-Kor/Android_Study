package com.kr.ac.kumoh.s20190613.s23w07intentdata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.kr.ac.kumoh.s20190613.s23w07intentdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    companion object { // static
        const val KEY_NAME = "location"
        const val IMAGE_MOUNTAIN = "san"
        const val IMAGE_SEA = "bada"
    }
    private lateinit var main: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        main.btnSea.setOnClickListener(this)
        main.btnMountain.setOnClickListener(this)
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != Activity.RESULT_OK)
            return@registerForActivityResult

        val result = it.data?.getIntExtra(
            ImageActivity.IMAGE_RESULT,
            ImageActivity.NONE
        ) // 없을 때 반환값
        val str = when (result) {
            ImageActivity.LIKE -> "좋아요"
            ImageActivity.DISLIKE -> "싫어요"
            else -> ""
        }
        when (it.data?.getStringExtra(ImageActivity.IMAGE_NAME)) {
            IMAGE_SEA -> main.btnSea.text = "바다 ($str)"
            IMAGE_MOUNTAIN -> main.btnMountain.text = "산 ($str)"
        }
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, ImageActivity::class.java)
        val value = when (p0?.id) {
            main.btnMountain.id -> {
                Toast.makeText(this, "산 이미지", Toast.LENGTH_SHORT).show()
                IMAGE_MOUNTAIN
            }

            main.btnSea.id -> {
                Toast.makeText(this, "바다 이미지", Toast.LENGTH_SHORT).show()
                IMAGE_SEA
            }

            else -> return
        }
        intent.putExtra(KEY_NAME, value)
        //startActivity(intent)
        startForResult.launch(intent)
    }
}