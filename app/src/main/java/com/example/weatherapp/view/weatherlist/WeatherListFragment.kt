package com.example.weatherapp.view.weatherlist

import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.view.weatherDetails.WeatherDetails
import com.example.weatherapp.viewmodel.AppState

class WeatherListFragment : Fragment(), onCityClick {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    var isWorld = false

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

        viewModel.getWeatherListForRussia()

        binding.weatherChangeRegionBtn.setOnClickListener {
            isWorld = !isWorld
            if(isWorld){
                viewModel.getWeatherListForWorld()
                binding.weatherChangeRegionBtn.setImageResource(R.drawable.ic_earth)
            }
            else{
                viewModel.getWeatherListForRussia()
                binding.weatherChangeRegionBtn.setImageResource(R.drawable.ic_russia)
            }
        }
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {

                binding.loading.visibility = View.GONE

                Toast.makeText(activity,"Произошла ошибка при получений данных!",Toast.LENGTH_SHORT).show();
            }
            AppState.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is AppState.SuccessMany -> {

                binding.loading.visibility = View.GONE

                binding.weatherListRecyclerView.adapter = WeatherListAdapter(appState.weatherList, this)

            }
        }
    }

    override fun onCityClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, WeatherDetails.newInstance(weather)
        ).addToBackStack("").commit()
    }

}