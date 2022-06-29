package com.example.weatherapp.model

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.domain.getRussianCities
import com.example.weatherapp.domain.getWorldCities

class RepositoryLocalImpl : RepositoryCitiesWeather, RepositorySingleCity {

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }

    override fun getListWeather(location: Location): List<Weather> {
        return when(location){
            Location.Russian -> {
                 getRussianCities()
            }
            Location.World -> {
                 getWorldCities()
            }
        }
    }

}