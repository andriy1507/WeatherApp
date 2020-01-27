package com.goryachok.forecastapp.view.recyclerviewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.model.domain.Forecast

class DailyForecastAdapter :
    RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    private var forecast: MutableList<Forecast> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_layout, parent, false)
        return DailyForecastViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        forecast[position].let { holder.bind(it) }
    }

    fun setNewItemList(forecast: List<Forecast>) {
        this.forecast = forecast.toMutableList()
        notifyItemRangeChanged(0, this.forecast.size - 1)
    }

    class DailyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var temp: TextView = itemView.findViewById(R.id.temp_textView)
        var desc: TextView = itemView.findViewById(R.id.desc_textView)
        var date: TextView = itemView.findViewById(R.id.date_textView)

        fun bind(item: Forecast) {
            date.text = item.dateText.substringBefore(" ")
            desc.text = item.weather.first().description
            temp.text = itemView.context.getString(R.string.temperature_template, item.main.temp)
        }
    }
}