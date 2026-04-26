package com.example.leedstrinity.app.ui.home.weather


import WeatherResponse
import com.example.leedstrinity.app.network.RetrofitInstance

class WeatherRepositoryImpl : WeatherRepository {

    override suspend fun getWeather(city: String): WeatherResponse {
        return RetrofitInstance.weatherApi.getWeather(
            city = city,
            apiKey = API_KEY
        )
    }

    companion object {
        private const val API_KEY = "ba79c5a90ce945a1db3842aa1c381fab"
    }
}

