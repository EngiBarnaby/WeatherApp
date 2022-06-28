package com.example.weatherapp.model

import com.example.weatherapp.domain.Weather

interface Repository {

    fun getWeather(lat : Double, lon : Double) : Weather

}