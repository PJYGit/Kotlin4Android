package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.extensions.ctx
import com.example.weatherapp.domain.ForecastList
import com.example.weatherapp.domain.ModelForecast
import kotlinx.android.synthetic.main.item_forecast.view.*
import com.squareup.picasso.Picasso

public interface OnItemClickListener {
    operator fun invoke(forecast: ModelForecast)
}

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: (ModelForecast) -> Unit) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx)
            .inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position:
        Int
    ) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.size()


    class ViewHolder(view: View, val itemClick: (ModelForecast) -> Unit) :
        RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: ModelForecast) {
            with(forecast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(itemView.icon)
                itemView.date.text = date
                itemView.description.text = description
                itemView.maxTemperature.text = "${high.toString()}"
                itemView.minTemperature.text = "${low.toString()}"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}