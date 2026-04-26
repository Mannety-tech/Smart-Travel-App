package com.example.leedstrinity.app.ui.home.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")
    object Home : NavRoutes("home")
    object Search : NavRoutes("search")
    object Itinerary : NavRoutes("itinerary")

    object ItineraryDetails : NavRoutes("itineraryDetails/{tripId}") {
        fun createRoute(tripId: String) = "itineraryDetails/$tripId"
    }

    object Profile : NavRoutes("profile")
}


