package com.example.leedstrinity.app.ui.home.weather

import WeatherResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepositoryImpl()
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                _weather.value = repository.getWeather(city)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}