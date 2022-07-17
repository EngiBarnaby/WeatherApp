package com.example.weatherapp.viewmodel

import com.example.weatherapp.model.WeatherDTO.WeatherDTO

sealed class WeatherDetailState {
    data class Success(val weatherDTO : WeatherDTO) : WeatherDetailState()
    data class Error(val error : Throwable) : WeatherDetailState()
    object Loading : WeatherDetailState()
}