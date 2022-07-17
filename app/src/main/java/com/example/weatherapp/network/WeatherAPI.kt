package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.utils.YANDEX_HEADER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/v2/informers")
    fun getWeather(
        @Header(YANDEX_HEADER) keyValue : String,
        @Query("lat") lat : Double,
        @Query("lon") lon : Double
    ) : Call<WeatherDTO>

}