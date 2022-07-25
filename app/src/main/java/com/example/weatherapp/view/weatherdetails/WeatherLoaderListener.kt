package com.example.weatherapp.view.weatherdetails

import com.example.weatherapp.model.WeatherDTO.WeatherDTO

interface WeatherLoaderListener {
    fun onLoaded(weatherDTO: WeatherDTO)
    fun onError(error : Throwable)
    fun onLoading()
}