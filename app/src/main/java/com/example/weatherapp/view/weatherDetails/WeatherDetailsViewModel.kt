package com.example.weatherapp.view.weatherDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.broadcast.AirPlaneBroadCast
import com.example.weatherapp.model.RepositoryDetailWeather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.model.weatherDetailRepositories.*
import com.example.weatherapp.viewmodel.WeatherDetailState
import java.io.IOException

class WeatherDetailsViewModel : ViewModel() {

    private val weatherData = MutableLiveData<WeatherDetailState>()

    lateinit var weatherRepository : RepositoryDetailWeather


    fun getWeatherData() : MutableLiveData<WeatherDetailState> {
        choiceRepository()
        return weatherData
    }

    private fun choiceRepository(){

        val num = (1..3).random()

        weatherRepository =when(num){
            1 -> {
                RepositoryDetailOkHttpImpl()
            }

            2 -> {
                RepositoryDetailLoaderImpl()
            }

            3 -> {
                RepositoryDetailRetrofit()
            }

            else -> {
                RepositoryDetailLocalImp()
            }
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
            Log.d("Error", "$error")
            weatherData.postValue(WeatherDetailState.Error(error))
        }

    }
}

//    fun getCityWeather(lat: Double, lon: Double){
//        repositoryWeatherCity.getWeather(lat, lon, onLoaderListener)
//    }

//    val weatherData : LiveData<AppState> = _weatherData

//private val repositoryWeatherCity : RepositorySingleCity = RepositoryRemoteImpl()
//
//private val onLoaderListener : WeatherLoaderListener = object : WeatherLoaderListener {
//    override fun onLoaded(weatherDTO: WeatherDTO) {
//        _weatherData.postValue(AppState.Success(weatherDTO))
//    }
//
//    override fun onError(error: Throwable) {
//        _weatherData.postValue(AppState.Error(error))
//    }
//
//    override fun onLoading() {
//        _weatherData.postValue(AppState.Loading)
//    }
//
//}