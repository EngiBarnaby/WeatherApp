package com.example.weatherapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.network.WeatherLoader
import com.example.weatherapp.view.weatherDetails.WeatherLoaderListener

class RepositoryRemoteImpl : RepositorySingleCity {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getWeather(lat: Double, lon: Double, listener: WeatherLoaderListener) {
        WeatherLoader.fetchWeatherData(lat, lon, listener)
    }

}