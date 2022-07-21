package com.example.weatherapp

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.weatherapp.database.WeatherHistoryDatabase
import com.example.weatherapp.utils.DB_WEATHER

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        private var weatherHistoryDatabase : WeatherHistoryDatabase?=null

        fun getMyApp() = myApp!!

        fun getWeatherHistoryDatabase() : WeatherHistoryDatabase {
            if(weatherHistoryDatabase == null){
                weatherHistoryDatabase = Room.databaseBuilder(
                    getMyApp(),
                    WeatherHistoryDatabase::class.java,
                    DB_WEATHER).build()
            }
            return weatherHistoryDatabase!!
        }
    }
}