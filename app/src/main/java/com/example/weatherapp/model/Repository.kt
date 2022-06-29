package com.example.weatherapp.model

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.viewmodel.AppState

fun interface RepositorySingleCity {
    fun getWeather(lat : Double, lon : Double) : Weather
}

fun interface RepositoryCitiesWeather {
    fun getListWeather(location : Location) : List<Weather>
}

sealed class Location{
    object Russian : Location()
    object World : Location()
}