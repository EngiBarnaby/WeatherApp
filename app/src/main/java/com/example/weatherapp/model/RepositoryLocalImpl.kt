package com.example.weatherapp.model

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.domain.getRussianCities
import com.example.weatherapp.domain.getWorldCities
import com.example.weatherapp.model.WeatherDTO.WeatherDTO

class RepositoryLocalImpl : RepositoryCitiesWeather {

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