package com.goryachok.forecastapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.model.Forecast

class DailyForecastAdapter(private val forecast: List<Forecast>) :
    RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    class DailyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var temp: TextView = itemView.findViewById(R.id.temp_textView)
        var desc: TextView = itemView.findViewById(R.id.desc_textView)
        var date: TextView = itemView.findViewById(R.id.date_textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_layout, parent, false)
        return DailyForecastViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return forecast.size / 8
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.temp.text = forecast[position * 8].main.temp.toString() + "\u00B0"
        holder.desc.text = forecast[position * 8].weather[0].description
        holder.date.text = forecast[position * 8].dtTxt.substringBefore(" ")
    }
}