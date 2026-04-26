package com.example.leedstrinity.app.flight


data class FlightResponse(
    val data: List<FlightData>
)

data class FlightData(
    val flight: FlightInfo,
    val departure: FlightTimeInfo,
    val arrival: FlightTimeInfo,
    val airline: AirlineInfo
)

data class FlightInfo(
    val iata: String?
)

data class FlightTimeInfo(
    val airport: String?,
    val scheduled: String?,
    val estimated: String?,
    val delay: Int?
)

data class AirlineInfo(
    val name: String?
)
