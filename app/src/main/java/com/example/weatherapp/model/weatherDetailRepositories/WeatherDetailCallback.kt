package com.example.weatherapp.model.weatherDetailRepositories

import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import java.io.IOException

interface WeatherDetailCallback {
    fun onResponse(weatherDTO: WeatherDTO)
    fun onError(error : IOException)
}