package com.example.weatherapp.view.weatherHistoryList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.ListWeatherCallback
import com.example.weatherapp.model.Location
import com.example.weatherapp.model.RepositoryAllWeatherFromRoom
import com.example.weatherapp.model.RepositoryHistoryRoomImp
import com.example.weatherapp.viewmodel.WeatherHistoryAppState
import java.io.IOException

class WeatherHistoryListViewModel : ViewModel() {

    val repository : RepositoryAllWeatherFromRoom = RepositoryHistoryRoomImp()

    var history = MutableLiveData<WeatherHistoryAppState>()

    fun getLiveData(): MutableLiveData<WeatherHistoryAppState> {
        return history
    }

    fun getHistory(){
        history.value = WeatherHistoryAppState.Loading
        repository.getAllWeather(callback)
    }

    private val callback = object : ListWeatherCallback {
        override fun onResponse(weatherList: List<Weather>) {
            history.postValue(WeatherHistoryAppState.SuccessList(weatherList))
        }

        override fun onFailure(e: IOException) {
            history.postValue(WeatherHistoryAppState.Error(e))
        }

    }
}