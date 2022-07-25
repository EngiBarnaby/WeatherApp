package com.example.weatherapp.view.weatherdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.RepositoryDetailWeather
import com.example.weatherapp.model.RepositoryHistoryRoomImp
import com.example.weatherapp.model.weatherDetailRepositories.*
import com.example.weatherapp.viewmodel.WeatherDetailState
import java.io.IOException

class WeatherDetailsViewModel() : ViewModel() {


    var fromDataBase: Boolean? = null

    private val weatherData = MutableLiveData<WeatherDetailState>()
    private var _connectionStatus = MutableLiveData<Boolean>()
    var connectionStatus : LiveData<Boolean> = _connectionStatus

    lateinit var weatherRepository : RepositoryDetailWeather
    var weatherSaveRepository  = RepositoryHistoryRoomImp()


    fun setConnectStatus(status : Boolean){
        _connectionStatus.value = status
    }


    fun getWeatherData() : MutableLiveData<WeatherDetailState> {
        choiceRepository()
        return weatherData
    }

    private fun choiceRepository(){

        val num = (1..3).random()


        if(connectionStatus.value!!){
            weatherRepository =when(num){
                1 -> {
                    RepositoryDetailOkHttpImpl()
                }
                2 -> {
                    RepositoryDetailRetrofit()
                }
                else -> {
                    RepositoryDetailLoaderImpl()
                }
            }

//            weatherSaveRepository = RepositoryHistoryRoomImp()
        }
        else{
            weatherRepository = RepositoryDetailLocalImp()
        }
    }

    fun getWeather(city : City){
        if(!fromDataBase!!){
            choiceRepository()
            weatherData.value = WeatherDetailState.Loading
            weatherRepository.getWeather(city, callBack)
        }
        else{
            weatherData.value = WeatherDetailState.Loading
            weatherSaveRepository.getWeather(city, callbackFromDB)
        }
    }


    private val callbackFromDB = object : WeatherDetailCallback {
        override fun onResponse(weather: Weather) {
            weatherData.postValue(WeatherDetailState.Success(weather))
        }
        override fun onError(error: IOException) {
            weatherData.postValue(WeatherDetailState.Error(error))
        }
    }

    private val callBack =  object : WeatherDetailCallback {
        override fun onResponse(weather: Weather) {
            if (connectionStatus.value!!){
                weatherSaveRepository.addWeather(weather)
            }
            weatherData.postValue(WeatherDetailState.Success(weather))
        }

        override fun onError(error: IOException) {
            weatherData.postValue(WeatherDetailState.Error(error))
        }

    }
}