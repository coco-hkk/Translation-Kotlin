package com.coco_hkk.translation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.coco_hkk.translation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 视图绑定
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 视图绑定
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        // 监听 numbers 若被点击则启动 numbers 的 activity
        val numbers: TextView = mBinding.numbers
        numbers.setOnClickListener {
            val numbersIntent = Intent(this@MainActivity, NumbersActivity::class.java).apply {}
            startActivity(numbersIntent)
        }

        val family: TextView = mBinding.family
        family.setOnClickListener {
            val familyIntent = Intent(this@MainActivity, FamilyActivity::class.java).apply {}
            startActivity(familyIntent)
        }

        val colors: TextView = mBinding.colors
        colors.setOnClickListener {
            val colorsIntent = Intent(this@MainActivity, ColorsActivity::class.java).apply {}
            startActivity(colorsIntent)
        }

        val phrases: TextView = mBinding.phrases
        phrases.setOnClickListener {
            val phrasesIntent = Intent(this@MainActivity, PhrasesActivity::class.java).apply {}
            startActivity(phrasesIntent)
        }
    }
}
