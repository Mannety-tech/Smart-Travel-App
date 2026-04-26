package com.example.leedstrinity.app.flight

import com.example.leedstrinity.app.flight.FlightResponse

interface FlightRepository {
    suspend fun getFlightStatus(flightNumber: String): FlightResponse?
}




