package com.example.weatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherHistoryEntity::class], version = 1)
abstract class WeatherHistoryDatabase : RoomDatabase() {
    abstract fun weatherHistoryDAO() : WeatherHistoryDAO
}