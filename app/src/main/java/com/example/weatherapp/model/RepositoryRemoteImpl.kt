package com.example.weatherapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.domain.City
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.model.weatherDetailRepositories.WeatherDetailCallback
import com.example.weatherapp.network.WeatherLoader
import com.example.weatherapp.view.weatherDetails.WeatherLoaderListener

class RepositoryRemoteImpl : RepositorySingleCity {

//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun getWeather(city : City, listener: WeatherLoaderListener) {
//        WeatherLoader.fetchWeatherData(city.lat, city.lon, listener)
//    }

    override fun getWeather(city: City, callback: WeatherDetailCallback) {
        TODO("Not yet implemented")
    }

}