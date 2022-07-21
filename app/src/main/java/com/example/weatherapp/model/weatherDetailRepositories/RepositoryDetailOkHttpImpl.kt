package com.example.weatherapp.model.weatherDetailRepositories

import android.util.Log
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.domain.City
import com.example.weatherapp.model.RepositoryDetailWeather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.utils.YANDEX_HEADER
import com.example.weatherapp.utils.YANDEX_WEATHER_URL
import com.example.weatherapp.utils.bindDTOWithCity
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryDetailOkHttpImpl : RepositoryDetailWeather {
    override fun getWeather(city : City, callback: WeatherDetailCallback) {
        val call = createOkHttp(city.lat, city.lon)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful && response.body != null) {
                    response.body?.let {
                        val responseStr = it.string()
                        val weather = Gson().fromJson(responseStr, WeatherDTO::class.java)
                        callback.onResponse(bindDTOWithCity(weather, city))
                    }
                } else {
                    callback.onError(okio.IOException("403 404"))
                }
            }
        })
    }
}

fun createOkHttp(lat: Double, lon: Double): Call {
    val client = OkHttpClient()
    val builder = Request.Builder()
    builder.addHeader(YANDEX_HEADER, BuildConfig.WEATHER_API_KEY)
    builder.url("$YANDEX_WEATHER_URL?lat=${lat}&lon=${lon}")
    val request: Request = builder.build()
    return client.newCall(request)
}