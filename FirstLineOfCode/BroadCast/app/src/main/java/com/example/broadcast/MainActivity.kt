package com.example.broadcast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forceOffline.setOnClickListener {
            // send force offline broadcast
            val intent = Intent("com.example.broadcast.FORCE_OFFLINE")
            sendBroadcast(intent)
        }
    }

}
