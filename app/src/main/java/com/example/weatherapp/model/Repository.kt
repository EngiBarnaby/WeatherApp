package com.example.weatherapp.model

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.view.weatherDetails.WeatherLoaderListener

fun interface RepositorySingleCity {
    fun getWeather(lat : Double, lon : Double, listener : WeatherLoaderListener)
}

fun interface RepositoryCitiesWeather {
    fun getListWeather(location : Location) : List<Weather>
}

sealed class Location{
    object Russian : Location()
    object World : Location()
}