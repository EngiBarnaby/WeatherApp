package com.example.weatherapp

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.example.weatherapp.broadcast.AirPlaneBroadCast
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.view.timer.TimerFragment
import com.example.weatherapp.view.weatherdetails.WeatherDetailsViewModel
import com.example.weatherapp.view.weatherlist.WeatherListFragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.view.weatherhistorylist.WeatherHistoryFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var detailsViewModel: WeatherDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailsViewModel = ViewModelProvider(this).get(WeatherDetailsViewModel::class.java)

        checkConnection()

        val receiver =AirPlaneBroadCast(){checkConnection()}
        registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
    }

    private fun checkConnection() {
        detailsViewModel.setConnectStatus(Settings.System.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) == 0)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_threads -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, TimerFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }

            R.id.menu_history -> {
                supportFragmentManager.apply {
                    val historyFragment = supportFragmentManager.findFragmentByTag("history-list")
                    if(historyFragment == null){
                        beginTransaction()
                            .replace(R.id.container, WeatherHistoryFragment(), "history-list")
                            .addToBackStack("")
                            .commit()
                    }
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}