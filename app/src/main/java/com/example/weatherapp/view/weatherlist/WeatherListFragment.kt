package com.example.weatherapp.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import com.example.weatherapp.viewmodel.AppState

class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    lateinit var binding : FragmentWeatherListBinding
    lateinit var viewModel: WeatherListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, object : Observer<AppState>{
            override fun onChanged(data: AppState) {
                renderData(data)
            }
        })
        viewModel.sendRequest()
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {
                Toast.makeText(getActivity(),"Произошла ошибка при получений данных!",Toast.LENGTH_SHORT).show();
            }
            AppState.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                val data = appState.weatherData

                binding.city.text = data.city.name
                binding.temperatureValue.text = data.temperature.toString()
                binding.feelsLikeValue.text = data.feelsLike.toString()
                binding.coordinates.text = "${data.city.lat}/${data.city.lon}"
            }
        }
    }

}