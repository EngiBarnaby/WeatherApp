package com.example.weatherapp

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.weatherapp.broadcast.AirPlaneBroadCast
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.view.timer.TimerFragment
import com.example.weatherapp.view.weatherdetails.WeatherDetailsViewModel
import com.example.weatherapp.view.weatherlist.WeatherListFragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.view.contacts.ContactsListFragment
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
                    val timerFragment = supportFragmentManager.findFragmentByTag("timer_fragment")
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    if(timerFragment == null){
                        beginTransaction()
                            .replace(R.id.container, TimerFragment(), "timer_fragment")
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    else{
                        fragmentTransaction.remove(timerFragment).commit()
                        supportFragmentManager.popBackStack()
                        beginTransaction()
                            .replace(R.id.container, TimerFragment(), "timer_fragment")
                            .addToBackStack("")
                            .commit()
                    }
                }
                true
            }

            R.id.menu_history -> {
                supportFragmentManager.apply {
                    val historyFragment = supportFragmentManager.findFragmentByTag("history_list")
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    if(historyFragment == null){
                        beginTransaction()
                            .replace(R.id.container, WeatherHistoryFragment(), "history_list")
                            .addToBackStack("")
                            .commit()
                    }
                    else{
                        fragmentTransaction.remove(historyFragment).commit()
                        supportFragmentManager.popBackStack()
                        beginTransaction()
                            .replace(R.id.container, WeatherHistoryFragment(), "history_list")
                            .addToBackStack("")
                            .commit()
                    }
                }
                true
            }

            R.id.menu_contacts -> {
                supportFragmentManager.apply {
                    val contactsFragment = supportFragmentManager.findFragmentByTag("contacts_fragment")
                    val fragmentTransaction = supportFragmentManager.beginTransaction()

                    if(contactsFragment == null){
                        beginTransaction()
                            .replace(R.id.container, ContactsListFragment(), "contacts_fragment")
                            .addToBackStack("")
                            .commit()
                    }
                    else{
                        fragmentTransaction.remove(contactsFragment).commit()
                        supportFragmentManager.popBackStack()
                        beginTransaction()
                            .replace(R.id.container, ContactsListFragment(), "contacts_fragment")
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