package com.example.weatherapp.view.weatherDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.broadcast.AirPlaneBroadCast
import com.example.weatherapp.model.RepositoryDetailWeather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.model.weatherDetailRepositories.*
import com.example.weatherapp.utils.Connection
import com.example.weatherapp.viewmodel.WeatherDetailState
import java.io.IOException

class WeatherDetailsViewModel : ViewModel() {

    private val weatherData = MutableLiveData<WeatherDetailState>()
    private var _connectionStatus = MutableLiveData(Connection.connectionStatus)
    var connectionStatus : LiveData<Boolean> = _connectionStatus

    lateinit var weatherRepository : RepositoryDetailWeather


//    fun checkConnectionStatus(){
//        connectionStatus.value = Connection.connectionStatus
//    }


    fun getWeatherData() : MutableLiveData<WeatherDetailState> {
        choiceRepository()
        return weatherData
    }

    private fun choiceRepository(){

        val num = (1..3).random()

//        checkConnectionStatus()

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
        }
        else{
            weatherRepository = RepositoryDetailLocalImp()
        }
    }

    fun getWeather(lat : Double, lon : Double){
        choiceRepository()
        weatherData.value = WeatherDetailState.Loading
        weatherRepository.getWeather(lat, lon, callBack)
    }

    private val callBack =  object : WeatherDetailCallback {
        override fun onResponse(weatherDTO: WeatherDTO) {
            weatherData.postValue(WeatherDetailState.Success(weatherDTO))
        }

        override fun onError(error: IOException) {
            weatherData.postValue(WeatherDetailState.Error(error))
        }

    }
}