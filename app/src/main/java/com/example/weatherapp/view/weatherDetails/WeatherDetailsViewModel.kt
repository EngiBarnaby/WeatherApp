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

    private val _weatherData = MutableLiveData<AppState>()

    val weatherData : LiveData<AppState> = _weatherData

    private val repositoryWeatherCity : RepositorySingleCity = RepositoryRemoteImpl()

    private val onLoaderListener : WeatherLoaderListener = object : WeatherLoaderListener {
        override fun onLoaded(weatherDTO: WeatherDTO) {
            _weatherData.postValue(AppState.Success(weatherDTO))
        }

        override fun onError(error: Throwable) {
            _weatherData.postValue(AppState.Error(error))
        }

        override fun onLoading() {
            _weatherData.postValue(AppState.Loading)
        }

    }

    fun getCityWeather(lat: Double, lon: Double){
        repositoryWeatherCity.getWeather(lat, lon, onLoaderListener)
    }



}