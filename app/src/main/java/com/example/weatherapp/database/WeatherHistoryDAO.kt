package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherHistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherHistoryEntity : WeatherHistoryEntity)

    @Query("SELECT * FROM weather_history_entity_table WHERE lat=:lat AND lon=:lon")
    fun getWeatherHistoryByLocation(lat:Double, lon:Double) : List<WeatherHistoryEntity>

    @Query("SELECT * FROM weather_history_entity_table")
    fun getAllWeatherHistory() : List<WeatherHistoryEntity>
}