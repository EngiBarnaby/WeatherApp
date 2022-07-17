package com.example.weatherapp.view.weatherDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.viewmodel.AppState
import com.example.weatherapp.viewmodel.WeatherDetailState
import com.google.android.material.snackbar.Snackbar

class WeatherDetails : Fragment() {

    lateinit var binding: FragmentWeatherDetailsBinding
    lateinit var viewModel: WeatherDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.let { args ->
            args.getParcelable<Weather>(BUNDLE_WEATHER)
        }


        viewModel = ViewModelProvider(this).get(WeatherDetailsViewModel::class.java)
        viewModel.getWeatherData().observe(viewLifecycleOwner) { appState ->
            if (weather != null) {
                checkResponse(weather, appState)
            }
        }

        if (weather != null) {
            viewModel.getWeather(weather.city.lat, weather.city.lon)
        }

    }

    private fun checkResponse(weather: Weather, appState: WeatherDetailState) {
        when (appState) {

            WeatherDetailState.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }

            is WeatherDetailState.Error -> {
                binding.loading.visibility = View.GONE
                binding.error.visibility = View.VISIBLE
                Snackbar.make(binding.root, "Ошибка загрузки", Snackbar.LENGTH_INDEFINITE)
                    .setAction(
                        "Повторить"
                    ) { viewModel.getWeather(weather.city.lat, weather.city.lon) }.show()
            }

            is WeatherDetailState.Success -> {
                binding.loading.visibility = View.GONE
                renderData(weather.apply {
                    weather.feelsLike = appState.weatherDTO.fact.feels_like
                    weather.temperature = appState.weatherDTO.fact.temp
                })
            }

        }
    }

    private fun renderData(weather: Weather) {
        with(binding) {
            city.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            coordinates.text = "${weather.city.lat}/${weather.city.lon}"
        }
    }

    companion object {
        const val BUNDLE_WEATHER = "BUNDLE_WEATHER"
        fun newInstance(weather: Weather): WeatherDetails {
            val fragment = WeatherDetails()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER, weather)
            }
            return fragment
        }
    }

}