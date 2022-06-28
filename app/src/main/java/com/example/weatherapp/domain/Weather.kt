package com.example.weatherapp.domain

data class Weather (
    val city : City = getMoscow(),
    val temperature : Int = 42,
    val feelsLike : Int = 13
)

data class City(
    val name : String,
    val lat : Double,
    val lon : Double
)

fun getMoscow() = City("Москва", 55.755826, 37.617299900000035)