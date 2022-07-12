package com.example.weatherapp.view.timer

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.lang.Thread.sleep

const val BUNDLE_TIMER_KEY = "BUNDLE_TIMER_KEY"
const val TIMER_NUM = "TIMER_NUM"

class TimerServiceIntent : IntentService("Timer") {
    override fun onHandleIntent(intent: Intent?) {
        var timerNum = intent?.let {
            it.getIntExtra(BUNDLE_TIMER_KEY, 0)
        }
        while (true){
            sleep(1000)
            sendNum(timerNum)

            if(timerNum == 0){
                sendNum(0)
                break
            }

            if (timerNum != null) {
                timerNum -= 1
            }
        }
    }

    private fun sendNum(timerNum: Int?) =
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
            putExtra(TIMER_NUM, timerNum)
            action = WAVE
        })
}



