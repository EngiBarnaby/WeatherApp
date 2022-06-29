package com.example.weatherapp.view.weatherlist

import com.example.weatherapp.domain.Weather

fun interface onCityClick {
    fun onCityClick(weather : Weather)
}