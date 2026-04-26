package com.example.leedstrinity.app.ui.home.navigation

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.leedstrinity.app.ui.home.HomeScreen
import com.example.leedstrinity.app.ui.home.auth.LoginScreen
import com.example.leedstrinity.app.ui.home.auth.RegisterScreen
import com.example.leedstrinity.app.ui.home.itinerary.CreateItineraryScreen
import com.example.leedstrinity.app.ui.home.itinerary.EditItineraryScreen
import com.example.leedstrinity.app.ui.home.itinerary.ItineraryScreen
import com.example.leedstrinity.app.ui.home.itinerary.ItineraryDetailsScreen
import com.example.leedstrinity.app.ui.home.profile.ProfileScreen
import com.example.leedstrinity.app.ui.home.search.SearchScreen
import com.example.leedstrinity.app.ui.home.weather.WeatherTestScreen
import com.example.leedstrinity.app.chat.ChatViewModel
import com.example.leedstrinity.app.chat.ChatViewModelFactory
import com.example.leedstrinity.app.chat.ChatScreen
import com.example.leedstrinity.app.directions.DirectionsScreen
import com.example.leedstrinity.app.directions.DirectionsViewModel
import com.example.leedstrinity.app.directions.MapTestScreen
import com.example.leedstrinity.app.flight.FlightRepositoryImpl
import com.example.leedstrinity.app.ui.home.weather.WeatherRepositoryImpl

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavRoutes.Login.route
) {
    Scaffold(
        bottomBar = {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            val showBottomBar = currentRoute !in listOf(
                NavRoutes.Login.route,
                NavRoutes.Register.route
            )

            if (showBottomBar) {
                BottomBar(navController)
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {

            // LOGIN
            composable(NavRoutes.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(NavRoutes.Home.route) {
                            popUpTo(NavRoutes.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(NavRoutes.Register.route)
                    }
                )
            }

            // REGISTER
            composable(NavRoutes.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(NavRoutes.Home.route) {
                            popUpTo(NavRoutes.Register.route) { inclusive = true }
                        }
                    },
                    onBackToLogin = { navController.popBackStack() }
                )
            }

            // HOME
            composable(NavRoutes.Home.route) {
                HomeScreen(navController)
            }

            // SEARCH
            composable(NavRoutes.Search.route) {
                SearchScreen(navController)
            }

            // ITINERARY LIST
            composable(NavRoutes.Itinerary.route) {
                ItineraryScreen(navController)
            }

            composable("createItinerary") {
                CreateItineraryScreen(navController)
            }

            // ITINERARY DETAILS
            composable(
                route = NavRoutes.ItineraryDetails.route,
                arguments = listOf(
                    navArgument("tripId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val tripId = backStackEntry.arguments?.getString("tripId") ?: ""

                ItineraryDetailsScreen(
                    navController = navController,
                    tripId = tripId
                )
            }

            composable("weatherTest") {
                WeatherTestScreen()
            }

            composable("directions_test") { backStackEntry ->
                val viewModel: DirectionsViewModel = viewModel(backStackEntry)
                DirectionsScreen(viewModel)
            }



            // ⭐⭐⭐ FIXED CHAT ROUTE ⭐⭐⭐
            composable("chat") {

                val context = LocalContext.current
                val application = context.applicationContext as Application

                val chatViewModel: ChatViewModel = viewModel(
                    factory = ChatViewModelFactory(
                        application = application,
                        weatherRepository = WeatherRepositoryImpl(),
                        flightRepository = FlightRepositoryImpl()
                    )
                )

                ChatScreen(viewModel = chatViewModel)
            }

            composable("mapTest") {
                MapTestScreen()
            }

            composable(
                route = "editItinerary/{tripId}",
                arguments = listOf(navArgument("tripId") { type = NavType.StringType })
            ) { backStackEntry ->

                val tripId = backStackEntry.arguments?.getString("tripId") ?: ""

                EditItineraryScreen(
                    navController = navController,
                    tripId = tripId
                )
            }

            // PROFILE
            composable(NavRoutes.Profile.route) {
                ProfileScreen(navController)
            }
        }
    }
}













