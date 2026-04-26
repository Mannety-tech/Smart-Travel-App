package com.example.leedstrinity.app.flight

import com.example.leedstrinity.app.flight.FlightResponse
import com.example.leedstrinity.app.network.RetrofitInstance



class FlightRepositoryImpl : FlightRepository {

    override suspend fun getFlightStatus(flightNumber: String): FlightResponse? {
        return try {
            RetrofitInstance.flightApi.getFlightStatus(
                flightNumber = flightNumber,
                apiKey = API_KEY
            )
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val API_KEY = "7f60f7254e5801e16f3b063eec46b845"
    }
}



