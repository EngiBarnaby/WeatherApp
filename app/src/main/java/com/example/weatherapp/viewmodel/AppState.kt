package com.example.weatherapp.viewmodel

import com.example.weatherapp.domain.Weather

sealed class AppState {
    data class Success(val weatherData : Weather) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}