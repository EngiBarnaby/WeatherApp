package com.example.weatherapp.model.WeatherDTO

data class WeatherDTO(
    val fact: Fact,
    val forecast: Forecast? = null,
    val info: Info? = null,
    val now: Int = 0,
    val now_dt: String = ""
)