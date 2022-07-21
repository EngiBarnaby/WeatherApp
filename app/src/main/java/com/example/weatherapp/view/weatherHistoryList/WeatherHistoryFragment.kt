package com.example.weatherapp.view.weatherHistoryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherHistoryBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.view.weatherDetails.WeatherDetails
import com.example.weatherapp.view.weatherlist.WeatherListAdapter
import com.example.weatherapp.view.weatherlist.WeatherListFragment
import com.example.weatherapp.view.weatherlist.onCityClick
import com.example.weatherapp.viewmodel.AppState
import com.example.weatherapp.viewmodel.WeatherHistoryAppState
import com.google.android.material.snackbar.Snackbar

class WeatherHistoryFragment : Fragment(), onCityClick {

    lateinit var binding : FragmentWeatherHistoryBinding
    lateinit var viewModel: WeatherHistoryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherHistoryListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner){
            renderData(it)
        }
        viewModel.getHistory()
    }

    private fun renderData(appState : WeatherHistoryAppState){
        when (appState) {
            is WeatherHistoryAppState.Error -> {

                binding.loading.visibility = View.GONE

//                binding.root.RequestError("Ошибка загрузки", "Повторить") {
//
//                }
            }
            WeatherHistoryAppState.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is WeatherHistoryAppState.SuccessList -> {

                with(binding) {
                    loading.visibility = View.GONE
                    weatherHistoryList.adapter =
                        HistoryAdapter(appState.weatherList, this@WeatherHistoryFragment)
                }

            }
            else -> {}
        }
    }

    fun View.RequestError(massage: String, btnText: String, block: (v: View) -> Unit) {
        Snackbar.make(this, massage, Snackbar.LENGTH_INDEFINITE).setAction(btnText, block).show()
    }

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    override fun onCityClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, WeatherDetails.newInstance(weather, true)
        ).addToBackStack("").commit()
    }
}