package com.example.weatherapp.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.RepositoryLocalImpl
import com.example.weatherapp.model.RepositoryRemoteImpl
import com.example.weatherapp.viewmodel.AppState

class WeatherListViewModel : ViewModel() {

    private val liveData = MutableLiveData<AppState>()

    lateinit var repository : Repository

    fun getData():MutableLiveData<AppState>{
        choiceRepository()
        return liveData
    }

    private fun choiceRepository(){
        repository = if(isConnection()){
            RepositoryRemoteImpl()
        }else{
            RepositoryLocalImpl()
        }
    }


    fun sendRequest(){
        liveData.value = AppState.Loading
        val randomNum = (0..3).random()

        Thread{
            Thread.sleep(1000L)
            if(randomNum == 1){
                liveData.postValue(AppState.Error(IllegalStateException("Ошибка получения данных")))
            }
            else{
                liveData.postValue(AppState.Success(repository.getWeather(55.755826, 37.617299900000035)))
            }
        }.start()
    }

    private fun isConnection(): Boolean {
        return false
    }

}