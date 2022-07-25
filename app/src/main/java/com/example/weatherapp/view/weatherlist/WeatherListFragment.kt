package com.example.weatherapp.view.weatherlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.utils.IS_WORLD
import com.example.weatherapp.view.weatherdetails.WeatherDetails
import com.example.weatherapp.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment(), onCityClick {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    private var isWorld = false

    lateinit var binding: FragmentWeatherListBinding
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
//        viewModel.getWeatherListForRussia()
        showListOfTown()

        binding.weatherChangeRegionBtn.setOnClickListener { changeListOfTown() }
    }

    private fun changeListOfTown() {
        isWorld = !isWorld
        if (isWorld) {
            viewModel.getWeatherListForWorld()
            binding.weatherChangeRegionBtn.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherListForRussia()
            binding.weatherChangeRegionBtn.setImageResource(R.drawable.ic_russia)
        }
        saveWorldState(isWorld)
    }

    private fun showListOfTown() {
        activity?.let {
            isWorld = it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_WORLD, true)
        }
        if (isWorld) {
            viewModel.getWeatherListForWorld()
            binding.weatherChangeRegionBtn.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherListForRussia()
            binding.weatherChangeRegionBtn.setImageResource(R.drawable.ic_russia)
        }
    }

    private fun saveWorldState(isWorld: Boolean) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref!!.edit()
        editor.putBoolean(IS_WORLD, isWorld)
        editor.apply()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {

                binding.loading.visibility = View.GONE

                binding.root.RequestError("Ошибка загрузки", "Повторить") {
                    if (isWorld) {
                        viewModel.getWeatherListForWorld()
                        binding.weatherChangeRegionBtn.setImageResource(R.drawable.ic_earth)
                    } else {
                        viewModel.getWeatherListForRussia()
                        binding.weatherChangeRegionBtn.setImageResource(R.drawable.ic_russia)
                    }
                }
            }
            AppState.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is AppState.SuccessMany -> {

                with(binding) {
                    loading.visibility = View.GONE
                    weatherListRecyclerView.adapter =
                        WeatherListAdapter(appState.weatherList, this@WeatherListFragment)
                }

            }
            else -> {}
        }
    }

    fun View.RequestError(massage: String, btnText: String, block: (v: View) -> Unit) {
        Snackbar.make(this, massage, Snackbar.LENGTH_INDEFINITE).setAction(btnText, block).show()
    }


    override fun onCityClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, WeatherDetails.newInstance(weather, false)
        ).addToBackStack("").commit()
    }

}