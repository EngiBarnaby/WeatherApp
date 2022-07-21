package com.example.weatherapp.model.weatherDetailRepositories

import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.domain.getRussianCities
import com.example.weatherapp.domain.getWorldCities
import com.example.weatherapp.model.RepositoryDetailWeather
import com.example.weatherapp.model.WeatherDTO.Fact
import com.example.weatherapp.model.WeatherDTO.WeatherDTO

class RepositoryDetailLocalImp : RepositoryDetailWeather {
    override fun getWeather(city : City, callBack: WeatherDetailCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city.lat == city.lat && it.city.lon == city.lon}
//        callBack.onResponse(convertToDTO(response.first()))
        callBack.onResponse(response.first())
    }

    private fun convertToDTO(weather : Weather) : WeatherDTO {
        val fact = Fact(feels_like = weather.feelsLike, temp = weather.temperature)
        return WeatherDTO(fact = fact)
    }

}