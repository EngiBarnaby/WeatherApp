package com.example.weatherapp.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.*
import com.example.weatherapp.viewmodel.AppState

class WeatherListViewModel : ViewModel() {

    private val liveData = MutableLiveData<AppState>()

    lateinit var repositoryCitiesWeather: RepositoryCitiesWeather
    lateinit var repositorySingleCity: RepositorySingleCity

    fun getData():MutableLiveData<AppState>{
        choiceRepository()
        return liveData
    }

    private fun choiceRepository(){
        repositorySingleCity = if(isConnection()){
            RepositoryRemoteImpl()
        }else{
            RepositoryLocalImpl()
        }

        repositoryCitiesWeather = RepositoryLocalImpl()
    }

    fun getWeatherListForRussia(){
        return sendRequest(Location.Russian)
    }

    fun getWeatherListForWorld(){
        return sendRequest(Location.World)
    }


    fun sendRequest(location : Location){
        liveData.value = AppState.Loading
        val randomNum = (0..3).random()

        Thread{
            Thread.sleep(1000L)
            if(randomNum == 1){
                liveData.postValue(AppState.Error(IllegalStateException("Ошибка получения данных")))
            }
            else{
                liveData.postValue(AppState.SuccessMany(repositoryCitiesWeather.getListWeather(location)))
            }
        }.start()
    }

    private fun isConnection(): Boolean {
        return false
    }

}