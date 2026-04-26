package com.example.leedstrinity.app.ui.home.itinerary

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.leedstrinity.app.ui.home.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryDetailsScreen(
    navController: NavController,
    tripId: String,
    viewModel: ItineraryViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val itinerary = viewModel.itineraries.collectAsState().value
        .firstOrNull { it.id.toString() == tripId }

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (itinerary == null) {
        Text("Loading...")
        return
    }

    // WEATHER STATE
    val weather by weatherViewModel.weather.collectAsState()

    // Load weather when screen opens
    LaunchedEffect(itinerary.destination) {
        weatherViewModel.loadWeather(itinerary.destination)
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(itinerary.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("editItinerary/${itinerary.id}")
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }

                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Destination
            Text("Destination", style = MaterialTheme.typography.titleMedium)
            Text(itinerary.destination, style = MaterialTheme.typography.bodyLarge)

            // Dates
            Text("Dates", style = MaterialTheme.typography.titleMedium)
            Text("${itinerary.startDate} → ${itinerary.endDate}", style = MaterialTheme.typography.bodyLarge)

            // Notes
            Text("Notes", style = MaterialTheme.typography.titleMedium)
            Text(
                itinerary.notes.ifBlank { "No notes added." },
                style = MaterialTheme.typography.bodyLarge
            )

            // WEATHER CARD
            weather?.let { w ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Weather in ${w.name}", style = MaterialTheme.typography.titleMedium)
                        Text("${w.main.temp}°C — ${w.weather[0].description}")
                        Text("Humidity: ${w.main.humidity}%")
                    }
                }
            }

            Divider()

            // Future features placeholder
            Text("Upcoming Features", style = MaterialTheme.typography.titleMedium)
            Text(
                "• Travel routes\n• Real‑time alerts",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    // DELETE CONFIRMATION DIALOG
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Itinerary") },
            text = { Text("Are you sure you want to delete this itinerary? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteItinerary(itinerary)
                        showDeleteDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}



