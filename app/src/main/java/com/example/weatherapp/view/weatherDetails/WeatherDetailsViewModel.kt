package com.example.weatherapp.view.weatherDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.RepositoryRemoteImpl
import com.example.weatherapp.model.RepositorySingleCity
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.viewmodel.AppState

class WeatherDetailsViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherDTO>()

    val weatherData : LiveData<WeatherDTO> = _weatherData

    private val repositoryWeatherCity : RepositorySingleCity = RepositoryRemoteImpl()

    fun getCityWeather(lat: Double, lon: Double){
        repositoryWeatherCity.getWeather(lat, lon){
            _weatherData.postValue(it)
        }
    }



}