package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.utils.DB_WEATHER

@Database(entities = [WeatherHistoryEntity::class], version = 1)
abstract class WeatherHistoryDatabase : RoomDatabase() {
    abstract fun weatherHistoryDAO() : WeatherHistoryDAO

//    companion object {
//        private var INSTANCE: WeatherHistoryDatabase? = null
//
//        fun getDatabase(context : Context) : WeatherHistoryDatabase {
//            return INSTANCE ?: synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    WeatherHistoryDatabase::class.java,
//                    DB_WEATHER
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//
//    }

}