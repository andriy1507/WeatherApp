package com.goryachok.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goryachok.core.model.Weather
import kotlinx.android.synthetic.main.forecast_item_layout.view.*

class DailyForecastAdapter :
    RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    private var forecast: MutableList<Weather> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_layout, parent, false)
        return DailyForecastViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int = forecast.size

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        forecast[position].let { holder.bind(it) }
    }

    fun setNewItemList(forecast: List<Weather>) {
        this.forecast = forecast.toMutableList()
        notifyItemRangeChanged(0, this.forecast.size - 1)
    }

    class DailyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val temp = itemView.temp_textView
        private val desc = itemView.desc_textView
        private val date = itemView.date_textView

        fun bind(item: Weather) {
            date.text = item.timeStamp.getDate()
            desc.text = item.description
            temp.text = itemView.context.getString(R.string.temperature_template, item.temp)
        }
    }
}