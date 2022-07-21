package com.example.weatherapp.viewmodel

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO

sealed class WeatherDetailState {
    data class Success(val weather : Weather) : WeatherDetailState()
    data class Error(val error : Throwable) : WeatherDetailState()
    object Loading : WeatherDetailState()
}