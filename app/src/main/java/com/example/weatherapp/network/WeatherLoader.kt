package com.example.weatherapp.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.utils.YANDEX_HEADER
import com.example.weatherapp.utils.YANDEX_WEATHER_URL
import com.example.weatherapp.utils.getLines
import com.example.weatherapp.view.weatherdetails.WeatherLoaderListener
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    @RequiresApi(Build.VERSION_CODES.N)
    fun fetchWeatherData(lat : Double, lon : Double, listener: WeatherLoaderListener){
        val uri = URL("$YANDEX_WEATHER_URL?lat=$lat&lon=$lon")
        var connection : HttpsURLConnection? = null

        connection = uri.openConnection() as HttpsURLConnection
        connection.readTimeout = 5000
        connection.addRequestProperty(YANDEX_HEADER, BuildConfig.WEATHER_API_KEY)

        Thread{
            listener.onLoading()
            try{
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                listener.onLoaded(weatherDTO)
            }
            catch (e : Exception){
                listener.onError(e)
            }
            finally {
                connection.disconnect()
            }
        }.start()

    }

}