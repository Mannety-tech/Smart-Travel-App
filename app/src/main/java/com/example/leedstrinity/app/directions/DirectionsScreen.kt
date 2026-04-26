package com.example.leedstrinity.app.directions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*

@Composable
fun DirectionsScreen(viewModel: DirectionsViewModel) {



    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    val routeInfo by viewModel.routeInfo.collectAsState()
    val polyline by viewModel.polyline.collectAsState()
    val weather by viewModel.weather.collectAsState()
    val safety by viewModel.safety.collectAsState()
    val mode by viewModel.mode.collectAsState()

    val cameraPositionState = rememberCameraPositionState()

    // Reload route when travel mode changes
    LaunchedEffect(mode) {
        if (origin.isNotBlank() && destination.isNotBlank()) {
            viewModel.loadRoute(origin, destination)
        }
    }

    // Auto-zoom when polyline updates
    LaunchedEffect(polyline) {
        if (polyline.size > 1) {
            val boundsBuilder = LatLngBounds.Builder()
            polyline.forEach { boundsBuilder.include(it) }
            val bounds = boundsBuilder.build()

            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 100),
                durationMs = 1200
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Directions", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = origin,
            onValueChange = { origin = it },
            label = { Text("Starting Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (origin.isNotBlank() && destination.isNotBlank()) {
                    viewModel.loadRoute(origin, destination)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Route")
        }

        Spacer(Modifier.height(20.dp))

        if (polyline.isNotEmpty()) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                cameraPositionState = cameraPositionState
            ) {
                Polyline(
                    points = polyline,
                    color = Color(0xFF1976D2),
                    width = 12f
                )

                Marker(
                    state = MarkerState(position = polyline.first()),
                    title = "Start"
                )

                Marker(
                    state = MarkerState(position = polyline.last()),
                    title = "Destination"
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ModeButton("driving", mode) { viewModel.setMode("driving") }
            ModeButton("walking", mode) { viewModel.setMode("walking") }
            ModeButton("transit", mode) { viewModel.setMode("transit") }
        }

        Spacer(Modifier.height(20.dp))

        if (routeInfo.isNotEmpty()) Text(routeInfo)
        if (weather.isNotEmpty()) Text(weather)
        if (safety.isNotEmpty()) Text(safety)
    }
}

@Composable
fun ModeButton(label: String, currentMode: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (currentMode == label) Color(0xFF1976D2) else Color.Gray
        )
    ) {
        Text(label.replaceFirstChar { it.uppercase() })
    }
}





