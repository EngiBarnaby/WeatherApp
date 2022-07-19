package com.example.weatherapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history_entity_table")
data class WeatherHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    var temperature : Int,
    var feelsLike : Int
)