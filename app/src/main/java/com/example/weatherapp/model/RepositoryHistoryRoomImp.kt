package com.example.weatherapp.model

import com.example.weatherapp.MyApp
import com.example.weatherapp.database.WeatherHistoryDatabase
import com.example.weatherapp.database.WeatherHistoryEntity
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.weatherDetailRepositories.WeatherDetailCallback
import com.example.weatherapp.view.weatherDetails.WeatherLoaderListener

class RepositoryHistoryRoomImp : RepositoryDetailWeather, RepositoryAllWeatherFromRoom,  RepositoryWeatherSave {

    override fun getWeather(city: City, callback: WeatherDetailCallback) {
        Thread{
            callback.onResponse(MyApp
                .getWeatherHistoryDatabase()
                .weatherHistoryDAO()
                .getWeatherHistoryByLocation(city.lat, city.lon).let {
                    convertHistoryEntityToWeather(it).last()
                }
            )
        }.start()
    }

    private fun convertHistoryEntityToWeather(entityList: List<WeatherHistoryEntity>): List<Weather> {
        return entityList.map {
            Weather(City(it.name, it.lat, it.lon), it.temperature, it.feelsLike)
        }
    }

    override fun getAllWeather(callback: ListWeatherCallback) {
        Thread{
            callback.onResponse(convertHistoryEntityToWeather(
                MyApp
                    .getWeatherHistoryDatabase()
                    .weatherHistoryDAO()
                    .getAllWeatherHistory()
            ))
        }.start()
    }

    override fun addWeather(weather: Weather) {
        Thread{
            MyApp.getWeatherHistoryDatabase().weatherHistoryDAO().insert(convertWeatherToEntity(weather))
        }.start()
    }

    private fun convertWeatherToEntity(weather: Weather): WeatherHistoryEntity {
        return WeatherHistoryEntity(0, weather.city.name, weather.city.lat,weather.city.lon, weather.temperature, weather.feelsLike)
    }

}