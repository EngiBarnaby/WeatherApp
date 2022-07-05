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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.let{ args ->
            args.getParcelable<Weather>(BUNDLE_WEATHER)
        }

        if(weather != null){
            renderData(weather)
        }

    }

    private fun renderData(weather: Weather){
        with(binding){
            city.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            coordinates.text = "${weather.city.lat}/${weather.city.lon}"
        }
    }

    companion object {
        const val BUNDLE_WEATHER = "BUNDLE_WEATHER"
        fun newInstance(weather: Weather) : WeatherDetails {
            val fragment = WeatherDetails()
              fragment.arguments = Bundle().apply {
                  putParcelable(BUNDLE_WEATHER, weather)
              }
            return fragment
        }
    }

}