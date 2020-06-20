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
import com.squareup.picasso.Picasso

public interface OnItemClickListener {
    operator fun invoke(forecast: ModelForecast)
}

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: OnItemClickListener) :
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
    class ViewHolder(view: View, val itemClick: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        private val iconView: ImageView
        private val dateView: TextView
        private val descriptionView: TextView
        private val maxTemperatureView: TextView
        private val minTemperatureView: TextView

        init {
            iconView = view.findViewById(R.id.icon)
            dateView = view.findViewById(R.id.date)
            descriptionView = view.findViewById(R.id.description)
            maxTemperatureView = view.findViewById(R.id.maxTemperature)
            minTemperatureView = view.findViewById(R.id.minTemperature)
        }

        fun bindForecast(forecast: ModelForecast) {
            with(forecast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(iconView)
                dateView.text = date
                descriptionView.text = description
                maxTemperatureView.text = "${high.toString()}"
                minTemperatureView.text = "${low.toString()}"
                itemView.setOnClickListener { itemClick(forecast) }
            }
        }
    }
}