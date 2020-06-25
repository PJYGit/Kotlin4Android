package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.broadcast.controller.ActivityCollector

/**
 * Father activity class of all the activities.
 */
open class BaseActivity : AppCompatActivity() {

    lateinit var receiver: ForceOfflineReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    override fun onResume() {
        super.onResume()
        // filter intent
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.broadcast.FORCE_OFFLINE")
        // register
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        // un-reg
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    /**
     * Force offline broadcast receiver
     */
    inner class ForceOfflineReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            AlertDialog.Builder(context).apply {
                setTitle("Warning")
                setMessage("Your account logged in at another device. You're forced to be offline.")
                // The alert dialog can not be canceled -> disable the back button
                setCancelable(false)
                setPositiveButton("OK") { _, _ ->
                    ActivityCollector.finishAll()
                    // restart the login activity
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                }
                show()
            }
        }
    }
}