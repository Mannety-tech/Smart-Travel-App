package com.example.leedstrinity.app.directions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leedstrinity.app.ui.home.weather.WeatherRepository
import com.example.leedstrinity.app.ui.home.weather.WeatherRepositoryImpl
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DirectionsViewModel(
    private val repo: DirectionsRepository = DirectionsRepository(),
    private val weatherRepo: WeatherRepository = WeatherRepositoryImpl()
) : ViewModel() {

    private val _routeInfo = MutableStateFlow("")
    val routeInfo: StateFlow<String> = _routeInfo

    private val _polyline = MutableStateFlow<List<LatLng>>(emptyList())
    val polyline: StateFlow<List<LatLng>> = _polyline

    private val _weather = MutableStateFlow("")
    val weather: StateFlow<String> = _weather

    private val _safety = MutableStateFlow("")
    val safety: StateFlow<String> = _safety

    private val _mode = MutableStateFlow("driving")
    val mode: StateFlow<String> = _mode

    fun setMode(newMode: String) {
        _mode.value = newMode
    }

    fun loadRoute(origin: String, destination: String) {
        viewModelScope.launch {
            try {
                val response = repo.getRoute(origin, destination, _mode.value)

                val route = response.routes.firstOrNull()
                if (route == null) {
                    _routeInfo.value = "No route found."
                    _polyline.value = emptyList()
                    return@launch
                }

                val leg = route.legs.firstOrNull()
                if (leg != null) {
                    _routeInfo.value =
                        "From: ${leg.start_location?.lat}, ${leg.start_location?.lng}\n" +
                                "To: ${leg.end_location?.lat}, ${leg.end_location?.lng}\n" +
                                "Distance: ${leg.distance?.text ?: "?"}\n" +
                                "Duration: ${leg.duration?.text ?: "?"}"
                } else {
                    _routeInfo.value = "Route found, but no leg information available."
                }

                val encoded = route.overview_polyline?.points
                if (encoded.isNullOrEmpty()) {
                    _polyline.value = emptyList()
                    _routeInfo.value = "Route found, but no polyline available."
                    return@launch
                }

                _polyline.value = PolylineDecoder.decode(encoded)

                loadWeather(destination)
                detectAirport(destination)

            } catch (e: Exception) {
                _routeInfo.value = "Unable to load route."
                _polyline.value = emptyList()
            }
        }
    }

    private fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                val w = weatherRepo.getWeather(city)
                val desc = w.weather.firstOrNull()?.description ?: "unknown"
                val temp = w.main.temp

                _weather.value = "Weather in $city: $desc, $temp°C"
                generateSafety(desc)

            } catch (e: Exception) {
                _weather.value = "Unable to load weather."
            }
        }
    }

    private fun detectAirport(destination: String) {
        val airports = listOf("MAN", "LHR", "LGW", "DXB", "JFK", "CDG")
        if (destination.uppercase() in airports) {
            _safety.value = "Destination is an airport. Check flights for delays."
        }
    }

    private fun generateSafety(desc: String) {
        _safety.value = when {
            "storm" in desc.lowercase() -> "⚠ Severe storm risk. Travel with caution."
            "rain" in desc.lowercase() -> "⚠ Heavy rain expected. Roads may be slippery."
            "snow" in desc.lowercase() -> "⚠ Snowfall may cause delays."
            "fog" in desc.lowercase() -> "⚠ Fog may reduce visibility."
            else -> "No major safety alerts."
        }
    }



}




