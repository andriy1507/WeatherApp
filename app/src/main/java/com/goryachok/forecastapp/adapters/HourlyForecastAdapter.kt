package com.goryachok.forecastapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.model.Forecast

class HourlyForecastAdapter(private val forecast: List<Forecast>) :
    RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {
    class HourlyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val temp: TextView = itemView.findViewById(R.id.temp_textView)
        val desc: TextView = itemView.findViewById(R.id.desc_textView)
        val date: TextView = itemView.findViewById(R.id.date_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_layout, parent, false)
        return HourlyForecastViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        holder.date.text = forecast[position].dtTxt.substringAfter(" ")
        holder.desc.text = forecast[position].weather[0].description
        holder.temp.text = forecast[position].main.temp.toString()+"\u00B0"
    }
}