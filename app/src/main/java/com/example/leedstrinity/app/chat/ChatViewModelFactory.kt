package com.example.leedstrinity.app.chat


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leedstrinity.app.flight.FlightRepository
import com.example.leedstrinity.app.ui.home.weather.WeatherRepository

class ChatViewModelFactory(
    private val application: Application,
    private val weatherRepository: WeatherRepository,
    private val flightRepository: FlightRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(application, weatherRepository, flightRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
