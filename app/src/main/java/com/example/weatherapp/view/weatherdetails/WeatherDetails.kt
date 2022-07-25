package com.example.weatherapp.view.weatherdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.utils.FROM_DB
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

        val fromDatabase = arguments?.let { args ->
            args.getBoolean(FROM_DB)
        }


        viewModel = ViewModelProvider(requireActivity()).get(WeatherDetailsViewModel::class.java)
        viewModel.fromDataBase = fromDatabase
        viewModel.getWeatherData().observe(viewLifecycleOwner) { appState ->
            if (weather != null) {
                checkResponse(weather, appState)
            }
        }

        viewModel.connectionStatus.observe(viewLifecycleOwner){
            if (weather != null) {
                viewModel.getWeather(weather.city)
            }
        }
//
//        if (weather != null && fromDatabase == false) {
//            viewModel.getWeather(weather.city)
//        }
//        else{
//            weather?.city?.let { viewModel.getWeatherFromDB(it) }
//        }

    }



    private fun checkResponse(weather: Weather, appState: WeatherDetailState) {
        when (appState) {

            WeatherDetailState.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }

            is WeatherDetailState.Error -> {
                binding.loading.visibility = View.GONE
                binding.error.visibility = View.VISIBLE
                Log.d("Error", "${appState.error}")
                Snackbar.make(binding.root, "Ошибка загрузки", Snackbar.LENGTH_INDEFINITE)
                    .setAction(
                        "Повторить"
                    ) { viewModel.getWeather(weather.city) }.show()
            }

            is WeatherDetailState.Success -> {
                binding.loading.visibility = View.GONE
                binding.error.visibility = View.GONE
                renderData(appState.weather)
            }

        }
    }

    private fun renderData(weather: Weather) {
        with(binding) {
            city.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            coordinates.text = "${weather.city.lat}/${weather.city.lon}"
            if(viewModel.connectionStatus.value!! ){
                icon.loadUrl("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
            }
            else{
                icon.setImageResource(R.drawable.ic_baseline_wifi_off_24)
            }
        }
    }

    fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry{add(SvgDecoder(this@loadUrl.context))}
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    companion object {
        const val BUNDLE_WEATHER = "BUNDLE_WEATHER"
        fun newInstance(weather: Weather, fromBase : Boolean): WeatherDetails {
            val fragment = WeatherDetails()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER, weather)
                putBoolean(FROM_DB, fromBase)
            }
            return fragment
        }
    }

}