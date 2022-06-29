package com.example.weatherapp.view.weatherDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.domain.Weather

class WeatherDetails : Fragment() {

    lateinit var binding : FragmentWeatherDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDetailsBinding.inflate(inflater)
        return binding.root
    }

    private fun renderData(weather: Weather){
            binding.city.text = weather.city.name
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()
            binding.coordinates.text = "${weather.city.lat}/${weather.city.lon}"
    }

    companion object {
        fun newInstance() = WeatherDetails()
    }

}