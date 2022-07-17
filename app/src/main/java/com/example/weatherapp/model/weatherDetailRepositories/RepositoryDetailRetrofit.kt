package com.example.weatherapp.model.weatherDetailRepositories

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.RepositoryDetailWeather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.network.WeatherAPI
import com.example.weatherapp.utils.YANDEX_BASE_URL
import com.google.gson.GsonBuilder
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryDetailRetrofit : RepositoryDetailWeather {
    override fun getWeather(lat: Double, lon: Double, callBack: WeatherDetailCallback) {
        val retrofit = Retrofit.Builder()
        retrofit.baseUrl(YANDEX_BASE_URL)
        retrofit.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        val api = retrofit.build().create(WeatherAPI::class.java)
        api.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon).enqueue(object : Callback<WeatherDTO>{
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                if(response.isSuccessful){
                    callBack.onResponse(response.body()!!)
                }
                else{
                    callBack.onError(IOException("403 404"))
                }
            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                callBack.onError(t as IOException)
            }

        })
    }
}