package com.example.weatherapp.view.weatherHistoryList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.WeatherListRecycleItemBinding
import com.example.weatherapp.domain.Weather

class HistoryAdapter(private val weatherData : List<Weather>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = WeatherListRecycleItemBinding.inflate(LayoutInflater.from(parent.context))
        return HistoryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
       return weatherData.size
    }

    inner class HistoryViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(weather : Weather){
            val binding = WeatherListRecycleItemBinding.bind(itemView)
            with(binding){
                cityName.text = weather.city.name
            }
        }
    }

}