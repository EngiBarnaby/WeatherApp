package com.example.weatherapp.model.WeatherDTO

data class Fact(
    val feels_like: Int,
    val icon: String = "bkn_n",
    val temp: Int,
)