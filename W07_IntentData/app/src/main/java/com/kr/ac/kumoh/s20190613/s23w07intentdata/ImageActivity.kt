package com.kr.ac.kumoh.s20190613.s23w07intentdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kr.ac.kumoh.s20190613.s23w07intentdata.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val IMAGE_NAME = "image name"
        const val IMAGE_RESULT = "image result"

        const val LIKE = 100
        const val DISLIKE = 101
        const val NONE = 0
    }
    private lateinit var main: ActivityImageBinding
    private lateinit var getKeyName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityImageBinding.inflate(layoutInflater)
        setContentView(main.root)

        getKeyName = intent.getStringExtra(MainActivity.KEY_NAME) ?: "none"
        val res = when (getKeyName) {
            MainActivity.IMAGE_MOUNTAIN -> R.drawable.san
            MainActivity.IMAGE_SEA -> R.drawable.bada
            else -> R.drawable.ic_launcher_foreground
        }
        main.image.setImageResource(res)

        main.btnLike.setOnClickListener(this)
        main.btnDislike.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent = Intent()
        val value = when (p0?.id) {
            main.btnLike.id -> LIKE
            main.btnDislike.id -> DISLIKE
            else -> NONE
        }
        intent.putExtra(IMAGE_NAME, getKeyName)
        intent.putExtra(IMAGE_RESULT, value)
        setResult(RESULT_OK, intent)
        finish()
    }
}