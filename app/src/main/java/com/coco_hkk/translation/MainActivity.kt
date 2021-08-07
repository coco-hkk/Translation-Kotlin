package com.coco_hkk.translation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numbers: TextView = findViewById(R.id.numbers)
        numbers.setOnClickListener {
            val numbersIntent = Intent(this@MainActivity, NumbersActivity::class.java).apply {}
            startActivity(numbersIntent)
        }

        val family: TextView = findViewById(R.id.family)
        family.setOnClickListener {
            val familyIntent = Intent(this@MainActivity, FamilyActivity::class.java).apply {}
            startActivity(familyIntent)
        }

        val colors: TextView = findViewById(R.id.colors)
        colors.setOnClickListener {
            val colorsIntent = Intent(this@MainActivity, ColorsActivity::class.java).apply {}
            startActivity(colorsIntent)
        }

        val phrases: TextView = findViewById(R.id.phrases)
        phrases.setOnClickListener {
            val phrasesIntent = Intent(this@MainActivity, PhrasesActivity::class.java).apply {}
            startActivity(phrasesIntent)
        }
    }
}
