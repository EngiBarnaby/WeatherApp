package com.example.weatherapp.view.weatherlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.databinding.WeatherListRecycleItemBinding
import com.example.weatherapp.domain.Weather

class WeatherListAdapter(private val weatherData : List<Weather>,private val callback : onCityClick) : RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherListRecycleItemBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

   inner class WeatherViewHolder(view : View):RecyclerView.ViewHolder(view){
        fun bind(weather : Weather){
            val binding = WeatherListRecycleItemBinding.bind(itemView)
            with(binding){
                cityName.text = weather.city.name
                root.setOnClickListener {
                    callback.onCityClick(weather)
                }
            }
        }
    }

}