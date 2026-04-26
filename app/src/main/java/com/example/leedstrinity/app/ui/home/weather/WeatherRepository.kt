package com.example.leedstrinity.app.ui.home.weather


import WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(city: String): WeatherResponse
}
