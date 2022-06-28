package com.example.weatherapp.model

import com.example.weatherapp.domain.Weather

class RepositoryLocalImpl : Repository {

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(500L)
        }.start()
        return Weather()
    }

}