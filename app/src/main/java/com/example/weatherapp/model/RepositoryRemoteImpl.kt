package com.example.weatherapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.network.WeatherLoader

class RepositoryRemoteImpl : RepositorySingleCity {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getWeather(lat: Double, lon: Double, block: (weather: WeatherDTO) -> Unit) {
        WeatherLoader.fetchWeatherData(lat, lon, block)
    }

}