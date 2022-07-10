package com.example.weatherapp.network

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.utils.YANDEX_WEATHER_URL
import com.example.weatherapp.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    @RequiresApi(Build.VERSION_CODES.N)
    fun fetchWeatherData(lat : Double, lon : Double, block : (weather : WeatherDTO) -> Unit){
        val uri = URL("$YANDEX_WEATHER_URL?lat=$lat&lon=$lon")
        var connection : HttpsURLConnection? = null

        connection = uri.openConnection() as HttpsURLConnection
        connection.readTimeout = 5000
        connection.addRequestProperty("X-Yandex-API-Key", "5dcd2d84-eaea-452e-8c9d-e5352f762f0d")

        Thread{
            try{
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                block(weatherDTO)
            }
            catch (e : Exception){
                Log.e("Error", "Fail connection: $e" )
            }
            finally {
                connection.disconnect()
            }
        }.start()

    }

}