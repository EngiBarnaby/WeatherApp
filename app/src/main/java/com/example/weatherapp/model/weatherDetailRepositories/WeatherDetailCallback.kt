package com.example.weatherapp.model.weatherDetailRepositories

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import java.io.IOException

interface WeatherDetailCallback {
    fun onResponse(weather: Weather)
    fun onError(error : IOException)
}