package com.example.weatherapp.model.weatherDetailRepositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.domain.City
import com.example.weatherapp.model.RepositoryDetailWeather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.utils.YANDEX_HEADER
import com.example.weatherapp.utils.YANDEX_WEATHER_URL
import com.example.weatherapp.utils.bindDTOWithCity
import com.example.weatherapp.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryDetailLoaderImpl : RepositoryDetailWeather {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getWeather(city : City, callBack: WeatherDetailCallback) {
        val uri = URL("$YANDEX_WEATHER_URL?lat=${city.lat}&lon=${city.lon}")
        var connection : HttpsURLConnection? = null

        connection = uri.openConnection() as HttpsURLConnection
        connection.readTimeout = 5000
        connection.addRequestProperty(YANDEX_HEADER, BuildConfig.WEATHER_API_KEY)

        Thread{
            try{
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                callBack.onResponse(bindDTOWithCity(weatherDTO, city))
            }
            catch (e : IOException){
                callBack.onError(e)
            }
            finally {
                connection.disconnect()
            }
        }.start()
    }
}