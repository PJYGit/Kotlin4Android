package com.example.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        button2.setOnClickListener {
            val intent = Intent()
            intent.putExtra("backmsg", "Go away first!")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("backmsg", "Go away first!")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
