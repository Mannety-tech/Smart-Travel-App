package com.example.leedstrinity.app.ui.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = NavRoutes.Home.route,
        label = "Home",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        route = NavRoutes.Search.route,
        label = "Search",
        icon = Icons.Default.Search
    ),
    BottomNavItem(
        route = NavRoutes.Itinerary.route,
        label = "Trips",
        icon = Icons.Default.List
    ),
    BottomNavItem(
        route = NavRoutes.Profile.route,
        label = "Profile",
        icon = Icons.Default.Person
    )
)

