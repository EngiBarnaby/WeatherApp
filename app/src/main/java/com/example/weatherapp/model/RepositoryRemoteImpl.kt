package com.example.weatherapp.model

import com.example.weatherapp.domain.Weather

class RepositoryRemoteImpl : Repository {

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(500L)
        }.start()
        return Weather()
    }
}