package com.example.weatherapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapter.ForecastListAdapter
import com.example.weatherapp.domain.RequestForecastCommand
import com.example.weatherapp.extensions.toast
import kotlinx.coroutines.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Created!")
        setContentView(R.layout.activity_main)
        // message.text = "Hello Kotlin!"

        forecastList.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            println("Thread launched")
            val result = RequestForecastCommand("94043").execute()
            println("Response received")
            withContext(Dispatchers.Main)
            {
                forecastList.adapter = ForecastListAdapter(result) { toast(it.date) }
            }
        }
    }
}
