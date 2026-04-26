package com.example.leedstrinity.app.directions

import android.util.Log
import com.example.leedstrinity.smarttravelappclean.BuildConfig
import java.net.URLEncoder

class DirectionsRepository {

    suspend fun getRoute(origin: String, destination: String, mode: String): DirectionsResponse {

        Log.d("ROUTE_DEBUG", "getRoute() CALLED")


        // Raw values from UI
        Log.d("ROUTE_DEBUG", "Origin (raw)='$origin'")
        Log.d("ROUTE_DEBUG", "Destination (raw)='$destination'")

        // Clean values
        val cleanOrigin = origin.trim()
        val cleanDestination = destination.trim()

        Log.d("ROUTE_DEBUG", "Origin (clean)='$cleanOrigin'")
        Log.d("ROUTE_DEBUG", "Destination (clean)='$cleanDestination'")

        // Encoded values
        val encodedOrigin = URLEncoder.encode(cleanOrigin, "UTF-8")
        val encodedDestination = URLEncoder.encode(cleanDestination, "UTF-8")

        Log.d("ROUTE_DEBUG", "Origin (encoded)='$encodedOrigin'")
        Log.d("ROUTE_DEBUG", "Destination (encoded)='$encodedDestination'")

        // Mode fallback
        val safeMode = if (mode.isBlank()) "driving" else mode

        Log.d("ROUTE_DEBUG", "Mode='$safeMode'")
        Log.d("ROUTE_DEBUG", "API_KEY='${BuildConfig.MAPS_API_KEY}'")

        // Final API call
        return RetrofitDirections.api.getRoute(
            encodedOrigin,
            encodedDestination,
            safeMode,
            BuildConfig.MAPS_API_KEY
        )
    }
}





