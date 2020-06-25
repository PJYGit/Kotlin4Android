package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d("MyBroadcastReceiver", "Message received.")
        Toast.makeText(context, "Message received.", Toast.LENGTH_SHORT).show()
        abortBroadcast()
    }
}
