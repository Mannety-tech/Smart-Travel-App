package com.example.leedstrinity.app.network

import com.example.leedstrinity.app.ui.home.weather.WeatherApi
import com.example.leedstrinity.app.flight.FlightApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // -------------------------
    // WEATHER API
    // -------------------------
    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    // -------------------------
    // FLIGHT API (AviationStack)
    // -------------------------
    private const val FLIGHT_BASE_URL = "http://api.aviationstack.com/v1/"

    val flightApi: FlightApi by lazy {
        Retrofit.Builder()
            .baseUrl(FLIGHT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightApi::class.java)
    }
}

