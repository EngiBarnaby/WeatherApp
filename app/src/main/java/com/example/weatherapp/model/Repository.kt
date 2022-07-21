package com.example.weatherapp.model

import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.weatherDetailRepositories.WeatherDetailCallback
import java.io.IOException

fun interface RepositorySingleCity {
    fun getWeather(city : City, callback : WeatherDetailCallback)
}

fun interface RepositoryAllWeatherFromRoom{
    fun getAllWeather(callback : ListWeatherCallback)
}

fun interface RepositoryWeatherSave{
    fun addWeather(weather : Weather)
}

fun interface RepositoryDetailWeather {
    fun getWeather(city : City, callBack : WeatherDetailCallback)
}

fun interface RepositoryCitiesWeather {
    fun getListWeather(location : Location) : List<Weather>
}

interface ListWeatherCallback{
    fun onResponse(weatherList: List<Weather>)
    fun onFailure(e: IOException)
}

sealed class Location{
    object Russian : Location()
    object World : Location()
}