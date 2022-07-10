package com.example.weatherapp.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.view.weatherDetails.WeatherDetails
import com.example.weatherapp.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

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
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner) { data -> renderData(data) }
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

                binding.root.RequestError("Ошибка загрузки", "Повторить"){
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
            AppState.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is AppState.SuccessMany -> {

                with(binding){
                    loading.visibility = View.GONE
                    weatherListRecyclerView.adapter = WeatherListAdapter(appState.weatherList, this@WeatherListFragment)
                }

            }
        }
    }

    fun View.RequestError(massage: String, btnText : String, block : (v : View) -> Unit){
        Snackbar.make(this, massage, Snackbar.LENGTH_INDEFINITE).setAction(btnText, block).show()
    }


    override fun onCityClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, WeatherDetails.newInstance(weather)
        ).addToBackStack("").commit()
    }

}