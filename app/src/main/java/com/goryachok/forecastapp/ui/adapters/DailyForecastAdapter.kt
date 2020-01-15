package com.goryachok.forecastapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.pojo.Forecast

class DailyForecastAdapter(private var forecast: List<Forecast>) :
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
        val context = holder.itemView.context
        holder.temp.text = context.getString(R.string.temperature_template, forecast[position * 8].main?.temp)
        holder.desc.text = forecast[position * 8].weather[0].description
        holder.date.text = forecast[position * 8].dateText.substringBefore(" ")
    }
    fun update(forecast: List<Forecast>){
        this.forecast = forecast
        notifyDataSetChanged()
    }
}