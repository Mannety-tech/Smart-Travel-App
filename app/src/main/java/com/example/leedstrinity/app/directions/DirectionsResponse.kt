package com.example.leedstrinity.app.directions

data class DirectionsResponse(
    val routes: List<Route> = emptyList(),
    val status: String? = null
)

data class Route(
    val legs: List<Leg> = emptyList(),
    val overview_polyline: OverviewPolyline? = null
)

data class Leg(
    val distance: TextValue? = null,
    val duration: TextValue? = null,
    val start_location: Location? = null,
    val end_location: Location? = null,
    val start_address: String? = null,
    val end_address: String? = null,
    val steps: List<Step> = emptyList()
)

data class Step(
    val polyline: Polyline? = null
)

data class Polyline(
    val points: String? = null
)

data class OverviewPolyline(
    val points: String? = null
)

data class TextValue(
    val text: String? = null,
    val value: Int? = null
)

data class Location(
    val lat: Double? = null,
    val lng: Double? = null
)





