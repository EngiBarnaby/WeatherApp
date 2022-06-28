package com.example.weatherapp.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Repository
import com.example.weatherapp.viewmodel.AppState

class WeatherListViewModel : ViewModel() {

    private val liveData = MutableLiveData<AppState>()

    lateinit var repository : Repository



}