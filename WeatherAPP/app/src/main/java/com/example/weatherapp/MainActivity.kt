package com.example.weatherapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapter.ForecastListAdapter
import com.example.weatherapp.adapter.OnItemClickListener
import com.example.weatherapp.domain.ModelForecast
import com.example.weatherapp.domain.RequestForecastCommand
import com.example.weatherapp.extensions.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // message.text = "Hello Kotlin!"

        val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)

        async() {
            val result = RequestForecastCommand("94043").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result,
                    object : OnItemClickListener {
                        override fun invoke(forecast: ModelForecast) {
                            toast(forecast.date)
                        }
                    })
            }
        }
    }
}
