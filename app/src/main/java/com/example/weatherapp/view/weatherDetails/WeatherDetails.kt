package com.example.weatherapp.view.weatherDetails

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
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
import com.example.weatherapp.broadcast.AirPlaneBroadCast
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.model.WeatherDTO.WeatherDTO
import com.example.weatherapp.viewmodel.WeatherDetailState
import com.google.android.material.snackbar.Snackbar

class WeatherDetails : Fragment() {

    lateinit var binding: FragmentWeatherDetailsBinding
    lateinit var viewModel: WeatherDetailsViewModel
    lateinit var receiver : BroadcastReceiver

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
        viewModel.connectionStatus.observe(viewLifecycleOwner){
            Log.d("Change", "Is change")
        }


        if (weather != null) {
            viewModel.getWeather(weather.city.lat, weather.city.lon)
        }

    }


//    private fun checkConnection() {
//        if (Settings.System.getInt(requireActivity().contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) == 0) {
//            viewModel.changeConnectionStatus(true)
//        } else {
//            viewModel.changeConnectionStatus(false)
//        }
//    }


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
                renderData(weather, appState.weatherDTO)
            }

        }
    }

    private fun renderData(weather: Weather ,weatherDTO : WeatherDTO) {
        with(binding) {
            city.text = weather.city.name
            temperatureValue.text = weatherDTO.fact.temp.toString()
            feelsLikeValue.text = weatherDTO.fact.feels_like.toString()
            coordinates.text = "${weather.city.lat}/${weather.city.lon}"
//            Picasso.get().load("https://kc-media-cdn-live.azureedge.net/cache/6/1/e/5/d/d/61e5ddce35cd9381e11de8f0257a88a302777bcc.jpg")
//                .into(icon)
            if(viewModel.connectionStatus.value!!){
                icon.loadUrl("https://yastatic.net/weather/i/icons/funky/dark/${weatherDTO.fact.icon}.svg")
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
        fun newInstance(weather: Weather): WeatherDetails {
            val fragment = WeatherDetails()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER, weather)
            }
            return fragment
        }
    }

}