package com.goryachok.forecastapp.view.recyclerviewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.model.domain.Forecast
import kotlinx.android.synthetic.main.forecast_item_layout.view.*

class HourlyForecastAdapter :
    RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {

    private var forecast: MutableList<Forecast> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_layout, parent, false)
        return HourlyForecastViewHolder(itemView)
    }

    override fun getItemCount(): Int = forecast.size

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        forecast[position].let { holder.bind(it) }
    }

    fun setNewItemList(forecast: List<Forecast>) {
        this.forecast = forecast.toMutableList()
        notifyItemRangeChanged(0, this.forecast.size - 1)
    }

    fun addNewItems(forecast: List<Forecast>) {
        val startIndex = this.forecast.size - 1

        this.forecast.addAll(forecast)
        val endIndex = startIndex + forecast.size

        notifyItemRangeChanged(startIndex, endIndex)
    }

    class HourlyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val temp = itemView.temp_textView
        private val desc = itemView.desc_textView
        private val date = itemView.date_textView

        fun bind(item: Forecast) {
            date.text = item.dateText.substringAfter(" ")
            desc.text = item.weather.first().description
            temp.text = itemView.context.getString(R.string.temperature_template, item.main.temp)
        }
    }
}