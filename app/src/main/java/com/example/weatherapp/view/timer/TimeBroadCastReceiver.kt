package com.example.weatherapp.view.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

const val WAVE = "WAVE"

class TimeBroadCastReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("BroadCast", "It's work")
    }
}