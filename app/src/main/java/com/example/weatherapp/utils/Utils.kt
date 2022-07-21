package com.example.weatherapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.WeatherDTO.Fact
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import java.io.BufferedReader
import java.util.stream.Collectors

@RequiresApi(Build.VERSION_CODES.N)
fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}

fun bindDTOWithCity(weatherDTO: WeatherDTO, city: City): Weather {
    val fact: Fact = weatherDTO.fact
    return (Weather(city, fact.temp, fact.feels_like, fact.icon))
}