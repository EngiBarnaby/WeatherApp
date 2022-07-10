package com.example.weatherapp.view.weatherDetails

import com.example.weatherapp.model.WeatherDTO.WeatherDTO

interface WeatherLoaderListener {
    fun onLoaded(weatherDTO: WeatherDTO)
    fun onError(error : Throwable)
    fun onLoading()
}