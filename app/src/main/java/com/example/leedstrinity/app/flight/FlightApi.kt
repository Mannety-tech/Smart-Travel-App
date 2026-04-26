package com.example.leedstrinity.app.flight

import com.example.leedstrinity.app.flight.FlightResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightApi {

    @GET("flights")
    suspend fun getFlightStatus(
        @Query("flight_iata") flightNumber: String,
        @Query("access_key") apiKey: String
    ): FlightResponse
}



