package com.example.weatherapp.viewmodel

import com.example.weatherapp.domain.Weather

sealed class WeatherHistoryAppState {
    data class SuccessDetail(val weatherDetail : Weather) : WeatherHistoryAppState()
    data class SuccessList(val weatherList : List<Weather>) : WeatherHistoryAppState()
    data class Error(val error : Throwable) : WeatherHistoryAppState()
    object Loading : WeatherHistoryAppState()
}