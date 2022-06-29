package com.example.weatherapp.model

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.domain.getRussianCities
import com.example.weatherapp.domain.getWorldCities

class RepositoryRemoteImpl : RepositorySingleCity {

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(300L)
        }.start()
        return Weather()
    }

}