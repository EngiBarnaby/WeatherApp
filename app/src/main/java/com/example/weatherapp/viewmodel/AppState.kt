package com.example.weatherapp.viewmodel

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO

sealed class AppState {
    data class SuccessMany(val weatherList : List<Weather>) : AppState()
    data class Success(val weatherData : WeatherDTO) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}