package com.example.weatherapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.weatherapp.view.weatherDetails.WeatherDetailsViewModel

class AirPlaneBroadCast(val checkConnection: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Режим полёта поменялся", Toast.LENGTH_SHORT).show()
        checkConnection()
    }

}