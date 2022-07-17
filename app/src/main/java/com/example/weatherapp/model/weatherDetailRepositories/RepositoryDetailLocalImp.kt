package com.example.weatherapp.model.weatherDetailRepositories

import com.example.weatherapp.domain.Weather
import com.example.weatherapp.domain.getRussianCities
import com.example.weatherapp.domain.getWorldCities
import com.example.weatherapp.model.RepositoryDetailWeather
import com.example.weatherapp.model.WeatherDTO.Fact
import com.example.weatherapp.model.WeatherDTO.WeatherDTO

class RepositoryDetailLocalImp : RepositoryDetailWeather {
    override fun getWeather(lat: Double, lon: Double, callBack: WeatherDetailCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city.lat == lat && it.city.lon == lon}
        callBack.onResponse(convertToDTO(response.first()))
    }

    private fun convertToDTO(weather : Weather) : WeatherDTO {
        val fact = Fact(feels_like = weather.feelsLike, temp = weather.temperature)
        return WeatherDTO(fact = fact)
    }

}