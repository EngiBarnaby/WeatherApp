package com.example.weatherapp.model

import com.example.weatherapp.domain.City
import com.example.weatherapp.model.weatherDetailRepositories.WeatherDetailCallback

class RepositoryRemoteImpl : RepositorySingleCity {

//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun getWeather(city : City, listener: WeatherLoaderListener) {
//        WeatherLoader.fetchWeatherData(city.lat, city.lon, listener)
//    }

    override fun getWeather(city: City, callback: WeatherDetailCallback) {
        TODO("Not yet implemented")
    }

}