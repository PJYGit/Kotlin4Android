package com.example.weatherapp.domain

data class ForecastList(
    val city: String,
    val country: String,
    val dailyForecast: List<ModelForecast>
) {
    operator fun get(position: Int): ModelForecast = dailyForecast[position]

    fun size(): Int = dailyForecast.size
}

data class ModelForecast(
    val date: String,
    val description: String,
    val high: Int,
    val low: Int,
    val iconUrl: String
)